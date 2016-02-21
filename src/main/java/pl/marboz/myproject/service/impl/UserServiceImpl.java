package pl.marboz.myproject.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marboz.myproject.model.User;
import pl.marboz.myproject.repository.BaseRepository;
import pl.marboz.myproject.service.UserService;

/**
 * Created by Marcin Bozek on 2016-02-18.
 */
@Service
public class UserServiceImpl extends UserService {

    @Autowired
    BaseRepository<User> userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public User register(User user) {
        long id = getRepository().save(user);
        user.setId(id);
        return user;
    }

    @Override
    public User get(String name) {
        User user = getRepository().findByName(name);
        return user;
    }
}
