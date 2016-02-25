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
import org.springframework.boot.actuate.health.MongoHealthIndicator;
import org.springframework.boot.actuate.health.RabbitHealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.client.RestTemplate;
import pl.marboz.myproject.dto.GitHubUserDTO;
import pl.marboz.myproject.dto.QuoteDTO;
import pl.marboz.myproject.dto.ValueDTO;
import pl.marboz.myproject.model.mongodb.Customer;
import pl.marboz.myproject.repository.mongodb.CustomerRepository;
import pl.marboz.myproject.service.GitHubLookupService;
import pl.marboz.myproject.service.ValueService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by Marcin Bozek on 2016-01-16.
 */
@SpringBootApplication
@EnableCaching
public class Application implements CommandLineRunner {

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitHealthIndicator rabbitHealthIndicator;

    @Autowired
    private GitHubLookupService gitHubLookupService;

    @Autowired
    private ValueService valueService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MongoHealthIndicator mongoHealthIndicator;

    private final Logger log = LogManager.getLogger(Application.class);

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

        if (rabbitHealthIndicator.health().getStatus() == Status.UP) {
            rabbitTemplate.convertAndSend(queueName, "Hi from RabbitMQ!");
            rabbitTemplate.setReceiveTimeout(5000);
            rabbitTemplate.receiveAndConvert(queueName);
        }

        asyncLookup();

        ValueDTO value = requestQuote(12L);
        requestQuote(value.getId());
        requestQuote(10L);
    }

    private void asyncLookup() throws ExecutionException, InterruptedException {
        Future<GitHubUserDTO> page1 = gitHubLookupService.findGitHubUser("PivotalSoftware");
        Future<GitHubUserDTO> page2 = gitHubLookupService.findGitHubUser("CloudFoundry");
        Future<GitHubUserDTO> page3 = gitHubLookupService.findGitHubUser("Spring-Projects");

        do {
            if (page3.isDone())
                log.info(page3.get());
            if (page2.isDone())
                log.info(page2.get());
            if (page1.isDone())
                log.info(page1.get());
        }
        while (!page3.isDone() || !page2.isDone() || !page1.isDone());

        mongoDBOps();
    }

    private ValueDTO requestQuote(Long id) {
        long start = System.currentTimeMillis();
        ValueDTO value = (id != null ? valueService.requestValueDTO(id) : valueService.requestRandomQuote());
        long elapsed = System.currentTimeMillis();
        log.info("{} Cache Miss [{}] - Elapsed Time [{} ms]", value, valueService.isCacheMiss(), elapsed - start);
        return value;
    }

    private void mongoDBOps() {
        if(!mongoHealthIndicator.health().getStatus().equals(Status.UP)) return;
        customerRepository.deleteAll();
        customerRepository.save(new Customer("Mariano", "Italiano"));
        customerRepository.save(new Customer("Monica", "Italiano"));

        for(Customer customer : customerRepository.findAll())
            log.info(customer);

        log.info(customerRepository.findByFirstName("Mariano"));

        for(Customer customer : customerRepository.findByLastName("Italiano"))
            log.info(customer);
    }
}
