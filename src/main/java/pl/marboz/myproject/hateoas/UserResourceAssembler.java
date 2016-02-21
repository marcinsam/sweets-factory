package pl.marboz.myproject.hateoas;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import pl.marboz.myproject.controller.UserController;
import pl.marboz.myproject.model.User;

/**
 * Created by Marcin Bozek on 2016-02-20.
 */
@Component
public class UserResourceAssembler extends ResourceAssemblerSupport<User, UserResource> {

    public UserResourceAssembler() {
        super(UserController.class, UserResource.class);
    }

    @Override
    public UserResource toResource(User entity) {
        UserResource userResource = new UserResource();
        userResource.setEmail(entity.getEmail());
        userResource.setName(entity.getName());
        userResource.setUserId(entity.getId());
        return userResource;
    }
}
