package pl.marboz.myproject.model;

/**
 * Created by Marcin Bozek on 2016-02-18.
 */
public class User extends BaseEntity {

    private String name;

    private String email;

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
