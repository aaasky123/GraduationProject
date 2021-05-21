package com.wxj.work.controller;

import com.wxj.work.entity.WorkManage;
import com.wxj.work.service.WorkManageService;
import com.wxj.work.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workManage")
public class WorkManageController {
    @Autowired
    private WorkManageService workManageService;

    @PostMapping("/queryManage")
    public Result queryManage(@RequestBody WorkManage workManage){
        return workManageService.queryManage(workManage);
    }

    @PostMapping("/addManage")
    public Result addManage(@RequestBody String addManageData){
        return workManageService.addManage(addManageData);
    }

    @PostMapping("/editManage")
    public Result editManage(@RequestBody WorkManage workManage){
        return workManageService.editManage(workManage);
    }

    @PostMapping("/delManage")
    public Result delManage(@RequestBody WorkManage workManage){
        return workManageService.delManage(workManage);
    }
}
