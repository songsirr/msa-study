package mj.song.microservices.core.management.controller;

import mj.song.microservices.core.management.domain.ManagementInfo;
import mj.song.microservices.core.management.service.ManagementService;
import mj.song.util.http.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManagementController {

    private final ServiceUtil serviceUtil;

    private final ManagementService managementService;

    @Autowired
    public ManagementController(ServiceUtil serviceUtil, ManagementService managementService){
        this.serviceUtil = serviceUtil;
        this.managementService = managementService;
    }

    @GetMapping(
            value = "/management/{userId}",
            produces = "application/json")
    public ManagementInfo getManagementInfo(@PathVariable long userId){
        return managementService.getManagementInfo(userId);
    }
}
