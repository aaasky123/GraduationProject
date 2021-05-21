package com.wxj.work.controller;

import com.wxj.work.entity.WorkFlowMark;
import com.wxj.work.service.WorkFlowMarkService;
import com.wxj.work.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workFlowMark")
public class WorkFlowMarkController {
    @Autowired
    private WorkFlowMarkService workFlowMarkService;

    @PostMapping("/addWorkFlowMark")
    public Result addWorkFlowMark(@RequestBody WorkFlowMark workFlowMark){
        return workFlowMarkService.addMark(workFlowMark);
    }

    @PostMapping("/queryWorkFlowMark")
    public Result queryWorkFlowMark(@RequestBody WorkFlowMark workFlowMark){
        return workFlowMarkService.queryWorkFlowMark(workFlowMark);
    }
}
