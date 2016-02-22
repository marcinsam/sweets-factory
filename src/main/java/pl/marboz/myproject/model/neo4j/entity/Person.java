package pl.marboz.myproject.model.neo4j.entity;

import net.openhft.koloboke.collect.set.hash.HashObjSets;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

/**
 * Created by Marcin Bozek on 2016-02-20.
 */
@NodeEntity
public class Person {

    @GraphId
    private Long id;

    private String name;

    @Relationship(type="TEAMMATE", direction= Relationship.UNDIRECTED)
    private Set<Person> teammates;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public void worksWith(Person person) {
        if(teammates==null)
            teammates = HashObjSets.<Person>newMutableSet();
        teammates.add(person);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Person> getTeammates() {
        return teammates;
    }

    public void setTeammates(Set<Person> teammates) {
        this.teammates = teammates;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Person{name='").append(name).append("\', teammates=" );

        if(teammates !=null) {
            teammates.stream().forEach(
                    p -> result.append("\t- ").append(p.name).append("\n")
            );
        }
        return result.toString();
    }
}
