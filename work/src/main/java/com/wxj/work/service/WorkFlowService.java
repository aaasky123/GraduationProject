package com.wxj.work.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wxj.work.entity.WorkFlow;
import com.wxj.work.mapper.WorkFlowMapper;
import com.wxj.work.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkFlowService {
    @Autowired
    private WorkFlowMapper workFlowMapper;

    public Result queryWorkFlow(String workId){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            JSONObject jsonObject= JSON.parseObject(workId);
            String workID=jsonObject.getString("workId");
            List<WorkFlow> queryWorkFlow=workFlowMapper.queryWorkFlow(workID);
            if(queryWorkFlow!=null|!queryWorkFlow.equals("")){
                result.setSuccess(true);
                result.setMsg("查询成功");
                result.setDetail(queryWorkFlow);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result queryHostId(WorkFlow workFlow){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            Long workId=workFlow.getWorkId();
            Long workFlowId=workFlow.getWorkFlowId();
            WorkFlow hostId=workFlowMapper.queryHostId(workId,workFlowId);
            Long hostId2=hostId.getHostId();
            result.setMsg("查询主办人id成功");
            result.setSuccess(true);
            result.setDetail(hostId2);
        }catch (Exception e){
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result queryState(WorkFlow workFlow){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            Long workId=workFlow.getWorkId();
            Long workFlowId=workFlow.getWorkFlowId();
            WorkFlow hostId=workFlowMapper.queryHostId(workId,workFlowId);
            Integer state=hostId.getState();
            result.setMsg("查询工作流程状态成功");
            result.setSuccess(true);
            result.setDetail(state);
        }catch (Exception e){
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result turnWorkFlow(WorkFlow workFlow){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            String endTime=workFlow.getEndTime();
            System.out.println(endTime);
            Long workId=workFlow.getWorkId();
            Long workFlowId=workFlow.getWorkFlowId();
            int turnWork= workFlowMapper.turnWork(endTime,workId,workFlowId);
            if(turnWork>0){
                WorkFlow queryCompanyId=workFlowMapper.queryCompanyId(workId);
                workFlow.setCompanyId(queryCompanyId.getCompanyId());
                Long nextWorkFlowId=workFlow.getWorkFlowId()+1;
                workFlow.setWorkFlowId(nextWorkFlowId);
                workFlow.setStartTime(workFlow.getEndTime());
                workFlow.setEndTime("");
                int createWorkFlow=workFlowMapper.createNexetWorkFlow(workFlow);
                if(createWorkFlow>0){
                    result.setMsg("工作移交成功");
                    result.setSuccess(true);
                    result.setDetail(createWorkFlow);
                }

            }else {
                result.setMsg("工作移交失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Result revokeWorkFlow(String revokeData){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            JSONObject jsonObject= JSON.parseObject(revokeData);
            Long workId=jsonObject.getLong("workId");
            Long workFlowId= jsonObject.getLong("workFLowId");
            Long revokeWorkFlowId=workFlowId-1;
            Integer deleteWorkFlow= workFlowMapper.deleteWorkFlow(workId,workFlowId);
            if (deleteWorkFlow>0){
                Integer revokeWorkFlow=workFlowMapper.updateWorkFlow(workId,revokeWorkFlowId);
                if(revokeWorkFlow>0){
                    result.setMsg("撤回流程成功");
                    result.setSuccess(true);
                    result.setDetail(revokeWorkFlow);
                }
            }else{
                result.setMsg("删除工作流程失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Result queryInformation(WorkFlow workFlow){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try{
            Long workId=workFlow.getWorkId();
            Long workFlowId=workFlow.getWorkFlowId();
            WorkFlow queryInformation= workFlowMapper.queryInformation(workId, workFlowId);
            String workFlowDescription=queryInformation.getTurnReason();
            if(workFlowDescription!=null){
                result.setSuccess(true);
                result.setMsg("查询工作流程说明成功");
                result.setDetail(workFlowDescription);
            }else{
                result.setMsg("查询工作流程说明成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
