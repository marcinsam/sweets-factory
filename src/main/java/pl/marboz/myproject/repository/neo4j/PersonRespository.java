package pl.marboz.myproject.repository.neo4j;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;
import pl.marboz.myproject.model.neo4j.entity.Person;

/**
 * Created by Marcin Bozek on 2016-02-20.
 */
@Repository
public interface PersonRespository extends GraphRepository<Person> {

    Person findByName(String name);

    Iterable<Person> findByTeammatesName(String name);
}
