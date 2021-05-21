package com.wxj.work.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.wxj.work.entity.Permission;
import com.wxj.work.entity.User;
import com.wxj.work.entity.Work;
import com.wxj.work.entity.WorkFlow;
import com.wxj.work.mapper.PermissionMapper;
import com.wxj.work.mapper.WorkFlowMapper;
import com.wxj.work.mapper.WorkMapper;
import com.wxj.work.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkService {
    @Autowired
    private WorkMapper workMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private WorkFlowMapper workFlowMapper;

    public Result createWork(Work work){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            Long number= work.getCreateUserId();
            User queryCompanyId=workMapper.queryCompanyId(number);
            Integer companyId=queryCompanyId.getCompanyId();
            work.setCompanyId(companyId);
            int createWork=workMapper.createNewWork(work);
            if(createWork>0){
                Long workId=work.getWorkId();
                int workFlowCompanyId=work.getCompanyId();
                Long hostId=work.getCreateUserId();
                String startTime=work.getCreateTime();
                String workFlowName=work.getWorkName();
                String turnReason=work.getStartDescription();
                int createWorkFlowWhenCreateWork= workFlowMapper.createWorkFlowWhenCreateWork(workId,workFlowCompanyId,hostId,startTime,workFlowName,turnReason);
                result.setSuccess(true);
                result.setMsg("新建工作成功");
                result.setDetail(createWorkFlowWhenCreateWork);
            }else{
                result.setMsg("新建工作失败");
            }

        }catch (Exception e){
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result queryOnWorkingWork(String number){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try{
            JSONObject jsonObject= JSON.parseObject(number);
            String loginUserNumber=jsonObject.getString("number");
            Long userId=Long.parseLong(loginUserNumber);
            Permission queryPermission= permissionMapper.queryPerrmission(userId);
            if(queryPermission.getPermissionValue().equals(1)||queryPermission.getPermissionValue().equals(3)){
                List<Work> workListPermission=workMapper.queryAllOnWorkingWorkPermission1(loginUserNumber);
                result.setMsg("查询成功");
                result.setSuccess(true);
                result.setDetail(workListPermission);
            }else{
                List<Work> workListNoPermission=workMapper.queryJoinInOnWorkingWork(loginUserNumber);
                result.setMsg("查询成功");
                result.setSuccess(true);
                result.setDetail(workListNoPermission);
            }
        }catch (Exception e){
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result queryFinishedWork(String number){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try{
            JSONObject jsonObject= JSON.parseObject(number);
            String loginUserNumber=jsonObject.getString("number");
            Long userId=Long.parseLong(loginUserNumber);
            Permission queryPermission= permissionMapper.queryPerrmission(userId);
            if(queryPermission!=null|!queryPermission.equals("")){
                List<Work> workListPermission=workMapper.queryAllFinishedWorkPermission1(loginUserNumber);
                result.setMsg("查询成功");
                result.setSuccess(true);
                result.setDetail(workListPermission);
            }else{
                List<Work> workListNoPermission=workMapper.queryJoinInFinishedWork(loginUserNumber);
                result.setMsg("查询成功");
                result.setSuccess(true);
                result.setDetail(workListNoPermission);
            }
        }catch (Exception e){
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result finishWork(String finishData){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            JSONObject jsonObject= JSON.parseObject(finishData);
            String endTime=jsonObject.getString("endTime");
            Long workId=jsonObject.getLong("workId");
            Long workFLowId=jsonObject.getLong("workFLowId");
            Integer finishWorkFLow= workFlowMapper.finishWorkFlow(endTime,workId,workFLowId);
            if(finishWorkFLow>0){
                Integer finishWork=workMapper.finishWork(endTime,workId);
                if(finishWork>0){
                    result.setMsg("办结工作成功");
                    result.setSuccess(true);
                    result.setDetail(finishWork);
                }else {
                    result.setMsg("办结工作失败");
                }
            }else{
                result.setMsg("办结流程失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
