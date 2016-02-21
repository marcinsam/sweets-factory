package pl.marboz.myproject.repository;

import org.springframework.data.repository.NoRepositoryBean;
import pl.marboz.myproject.model.BaseEntity;

/**
 * Created by Marcin Bozek on 2016-02-18.
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> {

    public int save(T t);

    public T findByName(String name);

    public void update(T t);

    public Boolean delete(T t);

}
