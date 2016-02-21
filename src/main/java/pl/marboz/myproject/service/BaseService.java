package pl.marboz.myproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.marboz.myproject.model.BaseEntity;
import pl.marboz.myproject.repository.BaseRepository;

/**
 * Created by Marcin Bozek on 2016-02-21.
 */
public abstract class BaseService<T extends BaseEntity> {

    @Autowired
    BaseRepository<T> repository;

    public BaseRepository<T> getRepository() {
        return repository;
    }
}
