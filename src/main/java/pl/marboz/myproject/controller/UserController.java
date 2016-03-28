package pl.marboz.myproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.web.bind.annotation.*;
import pl.marboz.myproject.hateoas.UserResource;
import pl.marboz.myproject.hateoas.UserResourceAssembler;
import pl.marboz.myproject.model.User;
import pl.marboz.myproject.service.UserService;

import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by Marcin Bozek on 2016-02-16.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    UserResourceAssembler userResourceAssembler;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource<UserResource>> register(@RequestBody User user) {
        User result =  userService.register(user);

        UserResource userResource = userResourceAssembler.toResource(result);
        Resource<UserResource> wrapped = new Resource<>(userResource, linkTo(UserController.class).slash(user).withSelfRel());

        return ResponseEntity.ok(wrapped);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource<UserResource>> get(@PathVariable(value = "name") String email) {
        User user = userService.get(email);

        UserResource userResource = userResourceAssembler.toResource(user);

        Resource<UserResource> wrapped = new Resource<>(userResource, linkTo(UserController.class).withSelfRel());

        return ResponseEntity.ok(wrapped);
    }

    @RequestMapping("/site-preference")
    public @ResponseBody String sitePreference(SitePreference sitePreference) {
        return sitePreference != null ? "Hello " + sitePreference.name() + " site preference!" : "Site preference not configured";
    }

}
