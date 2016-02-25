package pl.marboz.myproject.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.neo4j.io.fs.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marboz.myproject.model.neo4j.Person;
import pl.marboz.myproject.repository.neo4j.PersonRespository;

import java.io.File;
import java.io.IOException;

/**
 * Created by Marcin Bozek on 2016-02-20.
 */
@Service
public class Neo4jServiceImpl {

    @Autowired
    PersonRespository personRespository;

    private static final Logger log = LogManager.getLogger(Neo4jServiceImpl.class);


    public void sendAndReceive() throws IOException {
        //        Spring Data Neo4j does not support embedded Neo4j. Waiting for 4.1
        FileUtils.deleteRecursively(new File("sweets.db"));

        Person zenon = new Person("zenon");
        Person wiesiek = new Person("wiesiek");
        Person muniek = new Person("muniek");

        personRespository.save(zenon);
        personRespository.save(wiesiek);
        personRespository.save(muniek);

        zenon = personRespository.findByName(zenon.getName());
        zenon.worksWith(wiesiek);
        zenon.worksWith(muniek);
        personRespository.save(zenon);

        wiesiek = personRespository.findByName(wiesiek.getName());
        wiesiek.worksWith(muniek);
        personRespository.save(wiesiek);

        personRespository.findByTeammatesName(zenon.getName()).forEach(
                p->log.info(new StringBuilder(p.getName()).append(" wirks with Zenon")));

    }
}
