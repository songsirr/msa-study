package mj.song.microservices.core.user.service;

import mj.song.microservices.core.user.domain.User;

public interface AuthService {

    User getUserInfo(long userId);
}
