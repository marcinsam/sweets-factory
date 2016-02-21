package pl.marboz.myproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Marcin Bozek on 2016-02-20.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubUserDTO {

    private String name;

    private String blog;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    @Override
    public String toString() {
        return "GitHubUserDTO{" +
                "name='" + name + '\'' +
                ", blog='" + blog + '\'' +
                '}';
    }
}
