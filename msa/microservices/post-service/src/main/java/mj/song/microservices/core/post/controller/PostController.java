package mj.song.microservices.core.post.controller;

import mj.song.microservices.core.post.domain.Post;
import mj.song.microservices.core.post.service.PostService;
import mj.song.util.http.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    private final ServiceUtil serviceUtil;

    private final PostService postService;

    @Autowired
    public PostController(ServiceUtil serviceUtil, PostService postService){
        this.serviceUtil = serviceUtil;
        this.postService = postService;
    }

    @GetMapping(
            value = "/post/{postId}",
            produces = "application/json")
    public String getPost(@PathVariable long postId){

        return "test with " + postId + serviceUtil.getServiceAddress() + "\n";
    }

    @GetMapping(
            value = "/posts/{userId}",
            produces = "application/json")
    public List<Post> getPostsByUserId(@PathVariable long userId){

        return postService.getPostsByUserId(userId);
    }
}
