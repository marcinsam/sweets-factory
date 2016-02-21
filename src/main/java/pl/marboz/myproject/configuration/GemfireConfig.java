package pl.marboz.myproject.configuration;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.GemFireCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.LocalRegionFactoryBean;
import org.springframework.data.gemfire.support.GemfireCacheManager;

/**
 * Created by Marcin Bozek on 2016-02-21.
 */
@Configuration
public class GemfireConfig {

//    @Bean
//    Properties gemfireProperties() {
//        Properties gemfireProperties = new Properties();
//        gemfireProperties.setProperty("name", "DataGemFireCaching");
//        gemfireProperties.setProperty("mcast-port", "0");
//        return gemfireProperties;
//    }

    @Bean
    CacheFactoryBean gemfireCache() {
        CacheFactoryBean gemfireCache = new CacheFactoryBean();
        gemfireCache.setBeanName("DataGemFireCaching");
//        gemfireCache.setProperties(gemfireProperties());
        gemfireCache.setUseBeanFactoryLocator(false);
        return gemfireCache;
    }

    @Bean
    LocalRegionFactoryBean<Integer, Integer> quotesRegion(final GemFireCache cache) {
        LocalRegionFactoryBean<Integer, Integer> region = new LocalRegionFactoryBean<>();
        region.setClose(false);
        region.setCache(cache);
        region.setName("Quotes");
        region.setPersistent(false);
        return region;
    }

    @Bean
    GemfireCacheManager cacheManager(final Cache cache) {
        GemfireCacheManager cacheManager = new GemfireCacheManager();
        cacheManager.setCache(cache);
        return cacheManager;
    }
}
