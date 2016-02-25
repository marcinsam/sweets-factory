package pl.marboz.myproject.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.marboz.myproject.model.mongodb.Customer;

import java.util.List;

/**
 * Created by Marcin Bozek on 2016-02-25.
 */
public interface CustomerRepository extends MongoRepository<Customer, String> {

    Customer findByFirstName(String firstName);

    List<Customer> findByLastName(String lastName);
}
