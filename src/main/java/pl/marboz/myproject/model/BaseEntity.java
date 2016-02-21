package pl.marboz.myproject.model;

import org.springframework.hateoas.Identifiable;

import java.time.LocalDateTime;

/**
 * Created by Marcin Bozek on 2016-02-18.
 */
public class BaseEntity implements Identifiable<Long> {

    private Long id;

    private LocalDateTime created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
