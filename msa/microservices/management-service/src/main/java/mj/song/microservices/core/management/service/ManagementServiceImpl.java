package mj.song.microservices.core.management.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import mj.song.microservices.core.management.domain.ManagementInfo;
import mj.song.microservices.core.management.domain.Post;
import mj.song.microservices.core.management.domain.User;
import mj.song.util.exceptions.InvalidInputException;
import mj.song.util.exceptions.NotFoundException;
import mj.song.util.http.HttpErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManagementServiceImpl implements ManagementService {

    private static final Logger LOG = LoggerFactory.getLogger(ManagementService.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final String postServiceUrl;
    private final String userServiceUrl;

    @Autowired
    public ManagementServiceImpl(
            RestTemplate restTemplate,
            ObjectMapper mapper,
            @Value("${app.post-service.host}") String postServiceHost,
            @Value("${app.post-service.port}") int    postServicePort,
            @Value("${app.user-service.host}") String userServiceHost,
            @Value("${app.user-service.port}") int    userServicePort
    ) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        postServiceUrl = "http://" + postServiceHost + ":" + postServicePort + "/posts/";
        userServiceUrl = "http://" + userServiceHost + ":" + userServicePort + "/user/";
    }

    private List<Post> getPostsByUserId(long userId){
        try {
            String url = postServiceUrl + userId;
            LOG.debug("Will call getPostsByUserId API on URL: {}", url);

            List<Post> posts = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {}).getBody();
            LOG.debug("Found {} posts for a user with userId: {}", posts.size(), userId);

            return posts;
        } catch (Exception e){
            LOG.warn("Got an exception while requesting posts, return zero post: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    private User getUserById(long userId){
        try{
            String url = userServiceUrl + userId;
            LOG.debug("Will call getUserInfo API on URL: {}", url);

            User user = restTemplate.getForObject(url, User.class);
            LOG.debug("Found a user with id: {}", user.getId());

            return user;
        } catch (HttpClientErrorException ex){

            switch (ex.getStatusCode()) {

                case NOT_FOUND:
                    throw new NotFoundException(getErrorMessage(ex));

                case UNPROCESSABLE_ENTITY :
                    throw new InvalidInputException(getErrorMessage(ex));

                default:
                    LOG.warn("Got a unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
                    LOG.warn("Error body: {}", ex.getResponseBodyAsString());
                    throw ex;
            }
        }
    }

    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        } catch (IOException ioex) {
            return ex.getMessage();
        }
    }

    @Override
    public ManagementInfo getManagementInfo(long userId) {
        User user = getUserById(userId);
        List<Post> posts = getPostsByUserId(userId);
        ManagementInfo managementInfo = new ManagementInfo("user" + userId + "'s total info", user, posts);
        return managementInfo;
    }
}
