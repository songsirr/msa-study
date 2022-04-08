package mj.song.microservices.core.post.controller;

import mj.song.util.http.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final ServiceUtil serviceUtil;

    @Autowired
    public TestController(ServiceUtil serviceUtil){
        this.serviceUtil = serviceUtil;
    }

    @GetMapping(
            value = "/post/{postId}",
            produces = "application/json")
    public String getPost(@PathVariable long postId){

        return "test with " + postId + serviceUtil.getServiceAddress() + "\n";
    }
}
