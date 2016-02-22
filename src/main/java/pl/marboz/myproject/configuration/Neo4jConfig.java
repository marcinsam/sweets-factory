package pl.marboz.myproject.configuration;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.server.Neo4jServer;
import org.springframework.data.neo4j.server.RemoteServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Marcin Bozek on 2016-02-20.
 */
@Configuration
@EnableNeo4jRepositories(basePackages = "pl.marboz.myproject.repository.neo4j")
@EnableTransactionManagement
public class Neo4jConfig extends Neo4jConfiguration {

    @Value("${neo4j.server.username}")
    private String username;

    @Value("${neo4j.server.password}")
    private String password;

    @Bean
    @Override
    public Neo4jServer neo4jServer() {
        return new RemoteServer("http://username:password@localhost:8081", username, password);
    }

    @Bean
    @Override
    public SessionFactory getSessionFactory() {
        return new SessionFactory("pl.marboz.myproject.model.neo4j.entity");
    }
}
