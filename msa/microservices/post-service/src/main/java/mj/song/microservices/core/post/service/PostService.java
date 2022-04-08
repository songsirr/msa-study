package mj.song.microservices.core.post.service;

import mj.song.microservices.core.post.domain.Post;

import java.util.List;

public interface PostService {

    List<Post> getPostsByUserId(long userId);
}
