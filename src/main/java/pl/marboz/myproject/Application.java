package pl.marboz.myproject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.RabbitHealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import pl.marboz.myproject.dto.GitHubUserDTO;
import pl.marboz.myproject.dto.QuoteDTO;
import pl.marboz.myproject.service.GitHubLookupService;

import java.util.concurrent.Future;

/**
 * Created by Marcin Bozek on 2016-01-16.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RabbitHealthIndicator rabbitHealthIndicator;

    @Autowired
    GitHubLookupService gitHubLookupService;

    private static final Logger log = LogManager.getLogger(Application.class);

    private static final String SAMPLE_JSON = "https://docs-examples.firebaseio.com/rest/quickstart/users.json";

    private static final String RANDOM_QUOTES_URI = "http://gturnquist-quoters.cfapps.io/api/random";

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        QuoteDTO quote = restTemplate.getForObject(RANDOM_QUOTES_URI, QuoteDTO.class);
        log.info(quote.getType() + " " + quote.getValue().getQuote());

        String response = restTemplate.getForObject(SAMPLE_JSON, String.class);
        JsonNode jsonNode = objectMapper.readTree(response);
        log.info("name " + objectMapper.treeToValue(jsonNode.path("alanisawesome").path("name"), String.class));

        if(rabbitHealthIndicator.health().getStatus() == Status.UP) {
            rabbitTemplate.convertAndSend(queueName, "Hi from RabbitMQ!");
            rabbitTemplate.setReceiveTimeout(5000);
            rabbitTemplate.receiveAndConvert(queueName);
        }

        Future<GitHubUserDTO> page1 = gitHubLookupService.findGitHubUser("PivotalSoftware");
        Future<GitHubUserDTO> page2 = gitHubLookupService.findGitHubUser("CloudFoundry");
        Future<GitHubUserDTO> page3 = gitHubLookupService.findGitHubUser("Spring-Projects");

        do {
            if(page3.isDone())
                log.info(page3.get());
            if(page2.isDone())
                log.info(page2.get());
            if(page1.isDone())
                log.info(page1.get());
        }
        while(!page3.isDone() || !page2.isDone() || !page1.isDone());
    }
}
