package com.wxj.work.controller;

import com.wxj.work.entity.Work;
import com.wxj.work.service.WorkService;
import com.wxj.work.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/work")
public class WorkController {
    @Autowired
    private WorkService workService;

    @PostMapping("/createWork")
    public Result createNewWok(@RequestBody Work work){
        return workService.createWork(work);
    }

    @PostMapping("/queryOnWorkingWork")
    public Result queryOnWorkingWork(@RequestBody String number){
        return workService.queryOnWorkingWork(number);
    }

    @PostMapping("/queryFinishedWork")
    public Result queryFinishedWork(@RequestBody String number){
        return workService.queryFinishedWork(number);
    }

    @PostMapping("/finishWork")
    public Result finishWork(@RequestBody String finishData){
        return workService.finishWork(finishData);
    }
}
