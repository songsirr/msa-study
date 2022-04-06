package mj.song.microservices.core.post.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(
            value = "/post/{postId}",
            produces = "application/json")
    public String getPost(@PathVariable long postId){
        return "test with " + postId + "\n";
    }
}
