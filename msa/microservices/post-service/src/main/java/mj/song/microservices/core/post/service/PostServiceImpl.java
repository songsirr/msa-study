package mj.song.microservices.core.post.service;

import lombok.RequiredArgsConstructor;
import mj.song.microservices.core.post.domain.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private static final Logger LOG = LoggerFactory.getLogger(PostService.class);

    @Override
    public List<Post> getPostsByUserId(long userId) {
        List<Post> list = new ArrayList<>();
        for (long i = 1; i <= 3; i++){
            list.add(new Post(i, userId, "user" + userId + "'s post no." + i));
        }
        LOG.debug("call api getPostsByUserId of size {} with userId: {}", list.size(), userId);
        return list;
    }
}
