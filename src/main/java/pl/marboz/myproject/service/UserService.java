package pl.marboz.myproject.service;

import pl.marboz.myproject.model.User;

/**
 * Created by Marcin Bozek on 2016-02-18.
 */
public abstract class UserService extends BaseService<User> {

    public abstract User register(User user);

    public abstract User get(String email);
}
