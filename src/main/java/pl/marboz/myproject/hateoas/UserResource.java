package pl.marboz.myproject.hateoas;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by Marcin Bozek on 2016-02-20.
 */
public class UserResource extends ResourceSupport {

    private Long userId;

    private String name;

    private String email;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
