package com.wxj.work.controller;

import com.wxj.work.entity.Notifications;
import com.wxj.work.service.NotificationsService;
import com.wxj.work.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationsController {
    @Autowired
    private NotificationsService notificationsService;

    @PostMapping("/queryNotifications")
    public Result queryNotifications(@RequestBody String userId){
        return notificationsService.queryNotifications(userId);
    }

    @PostMapping("/insertNotifications")
    public Result insertNotifications(@RequestBody Notifications notifications){
        return notificationsService.addNotifications(notifications);
    }
}
