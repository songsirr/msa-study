package mj.song.microservices.core.user.service;

import lombok.RequiredArgsConstructor;
import mj.song.microservices.core.user.domain.User;
import mj.song.util.exceptions.InvalidInputException;
import mj.song.util.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements AuthService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User getUserInfo(long userId) {
        if (userId == 0) throw new InvalidInputException("Invalid id:" + userId);
        if (userId == 404) throw new NotFoundException("No found for user with id:" + userId);
        LOG.debug("call api getUserInfo with userId: {}", userId);
        return new User(userId, "user"+userId, "0100000000" + userId);
    }
}
