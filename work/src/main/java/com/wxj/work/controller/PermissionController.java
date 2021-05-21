package com.wxj.work.controller;

import com.wxj.work.entity.Permission;
import com.wxj.work.service.PermissionService;
import com.wxj.work.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @PostMapping("/queryPermission")
    public Result queryPermission(@RequestBody String userId){
        return permissionService.queryPerrmission(userId);
    }

    @PostMapping("/queryAllUserPermission")
    public Result queryAllUserPermission(@RequestBody String username){
        return permissionService.queryAllUserPermission(username);
    }

    @PostMapping("/updatePermission")
    public Result updatePermission(@RequestBody Permission permission){
        return permissionService.updatePermission(permission);
    }

}
