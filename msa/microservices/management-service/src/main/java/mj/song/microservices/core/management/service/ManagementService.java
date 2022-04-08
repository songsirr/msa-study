package mj.song.microservices.core.management.service;

import mj.song.microservices.core.management.domain.ManagementInfo;

public interface ManagementService {

    ManagementInfo getManagementInfo(long userId);
}
