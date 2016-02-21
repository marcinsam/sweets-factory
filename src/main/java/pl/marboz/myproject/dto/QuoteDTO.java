package pl.marboz.myproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Marcin Bozek on 2016-02-16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteDTO {

    private String type;
    private ValueDTO value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ValueDTO getValue() {
        return value;
    }

    public void setValue(ValueDTO value) {
        this.value = value;
    }
}
