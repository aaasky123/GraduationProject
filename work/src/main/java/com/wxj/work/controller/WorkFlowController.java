package com.wxj.work.controller;

import com.wxj.work.entity.Work;
import com.wxj.work.entity.WorkFlow;
import com.wxj.work.service.WorkFlowService;
import com.wxj.work.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workFlow")
public class WorkFlowController {
    @Autowired
    private WorkFlowService workFlowService;

    @PostMapping("/queryWorkFlow")
    public Result queryWorkFlow(@RequestBody String workId){
        return workFlowService.queryWorkFlow(workId);
    }

    @PostMapping("/queryHostId")
    public Result queryHostId(@RequestBody WorkFlow workFlow){
        return workFlowService.queryHostId(workFlow);
    }

    @PostMapping("/queryState")
    public Result queryState(@RequestBody WorkFlow workFlow){
        return workFlowService.queryState(workFlow);
    }

    @PostMapping("/turnWorkFlow")
    public Result turnWorkFlow(@RequestBody WorkFlow workFlow){
        return workFlowService.turnWorkFlow(workFlow);
    }

    @PostMapping("/revokeWorkFlow")
    public Result revokeWorkFlow(@RequestBody String revokeData){
        return workFlowService.revokeWorkFlow(revokeData);
    }

    @PostMapping("/queryInformation")
    public Result queryInformation(@RequestBody WorkFlow workFlow){ return workFlowService.queryInformation(workFlow);}
}