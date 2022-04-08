package mj.song.microservices.core.user.controller;

import mj.song.microservices.core.user.domain.User;
import mj.song.microservices.core.user.service.AuthService;
import mj.song.util.http.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final ServiceUtil serviceUtil;

    private final AuthService authService;

    @Autowired
    public UserController(ServiceUtil serviceUtil, AuthService authService){
        this.serviceUtil = serviceUtil;
        this.authService = authService;
    }

    @GetMapping(
            value = "/user/{userId}",
            produces = "application/json")
    public User getUserById(@PathVariable long userId){

        return authService.getUserInfo(userId);
    }
}
