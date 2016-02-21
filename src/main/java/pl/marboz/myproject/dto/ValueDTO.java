package pl.marboz.myproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.util.ObjectUtils;

/**
 * Created by Marcin Bozek on 2016-02-16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValueDTO {

    private Long id;
    private String quote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValueDTO valueDTO = (ValueDTO) o;

        return ObjectUtils.nullSafeEquals(this.getId(), valueDTO.getId());

    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + ObjectUtils.nullSafeHashCode(getId());
        return result;
    }

    @Override
    public String toString() {
        return "ValueDTO{" +
                "quote='" + quote + '\'' +
                '}';
    }
}
