package pl.marboz.myproject.service;

import pl.marboz.myproject.model.User;

/**
 * Created by Marcin Bozek on 2016-02-18.
 */
public interface UserService {

    User register(User user);

    User get(String email);
}
