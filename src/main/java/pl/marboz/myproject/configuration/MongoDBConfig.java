package pl.marboz.myproject.configuration;

import org.springframework.boot.actuate.health.MongoHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by Marcin Bozek on 2016-02-25.
 */
@Configuration
public class MongoDBConfig {

    @Bean
    public MongoHealthIndicator mongoHealthIndicator(MongoTemplate mongoTemplate) {
        return new MongoHealthIndicator(mongoTemplate);
    }
}
