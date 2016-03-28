package pl.marboz.myproject.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Marcin Bozek on 2016-02-25.
 */
@Configuration
public class WebMvcConfig  extends WebMvcConfigurerAdapter{

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/user/**").allowedOrigins("http://localhost:8080");
    }
}
