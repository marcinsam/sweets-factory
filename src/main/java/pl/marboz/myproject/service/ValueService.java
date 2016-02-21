package pl.marboz.myproject.service;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.marboz.myproject.dto.QuoteDTO;
import pl.marboz.myproject.dto.ValueDTO;

import java.util.Collections;
import java.util.Map;

/**
 * Created by Marcin Bozek on 2016-02-21.
 */
@Service
public class ValueService {

    private static final String ID_BASED_QUOTE_SERVICE_URL = "http://gturnquist-quoters.cfapps.io/api/{id}";
    private static final String RANDOM_QUOTE_SERVICE_URL = "http://gturnquist-quoters.cfapps.io/api/random";

    private volatile boolean cacheMiss = false;

    private RestTemplate restTemplate = new RestTemplate();

    public boolean isCacheMiss() {
        boolean cacheMiss = this.cacheMiss;
        this.cacheMiss = false;
        return cacheMiss;
    }

    @Cacheable("Quotes")
    public ValueDTO requestValueDTO(Long id) {
        setCacheMiss();
        return requestValueDTO(ID_BASED_QUOTE_SERVICE_URL, Collections.singletonMap("id", id));
    }

    @CachePut(cacheNames = "Quotes", key = "#result.id")
    public ValueDTO requestRandomQuote() {
        setCacheMiss();
        return requestValueDTO(RANDOM_QUOTE_SERVICE_URL);
    }

    private ValueDTO requestValueDTO(String url) {
        return requestValueDTO(url, Collections.emptyMap());
    }

    private ValueDTO requestValueDTO(String url, Map<String, Long> map) {
        QuoteDTO quoteDTO = restTemplate.getForObject(url, QuoteDTO.class, map);
        return quoteDTO.getValue();
    }

    public void setCacheMiss() {
        this.cacheMiss = true;
    }
}
