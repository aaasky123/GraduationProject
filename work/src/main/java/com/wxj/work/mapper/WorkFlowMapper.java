package com.wxj.work.mapper;

import com.wxj.work.entity.WorkFlow;
import com.wxj.work.entity.WorkFlowExample;
import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface WorkFlowMapper {
    long countByExample(WorkFlowExample example);

    int deleteByExample(WorkFlowExample example);

    int insert(WorkFlow record);

    int insertSelective(WorkFlow record);

    List<WorkFlow> selectByExample(WorkFlowExample example);

    int updateByExampleSelective(@Param("record") WorkFlow record, @Param("example") WorkFlowExample example);

    int updateByExample(@Param("record") WorkFlow record, @Param("example") WorkFlowExample example);

    /**
     * 新建工作时的创建工作流程
     */
    @Insert("insert into work_flow (work_id, work_flow_id, company_id, host_id, start_time, state, work_flow_name,turn_reason) values (#{workId},1,#{companyId},#{hostId},#{startTime},0,#{workFlowName},#{turnReason});")
    int createWorkFlowWhenCreateWork(@Param("workId") Long workId,@Param("companyId") int companyId,@Param("hostId") Long hostId,@Param("startTime") String startTime,@Param("workFlowName")String workFlowName,@Param("turnReason")String turnReason);

    /**
     * 根据workId查找工作流程
     */
    @Select("select * from work_flow where work_id=#{workID};")
    List<WorkFlow> queryWorkFlow(String workID);

    /**
     * 根据工作id和流程id查找主办人id
     */
    @Select("select host_id from work_flow where work_id=#{workId} and work_flow_id=#{workFlowId};")
    WorkFlow queryHostId(@Param("workId") Long workId,@Param("workFlowId") Long WorkFlowId);

    /**
     * 根据工作id和流程id查找流程状态
     */
    @Select("select state from work_flow where work_id=#{workId} and work_flow_id=#{workFlowId};")
    WorkFlow queryState(@Param("workId") Long workId,@Param("workFlowId") Long WorkFlowId);

    /**
     * 工作移交
     */
    @Update("update work_flow set end_time=#{endTime},state=1 where work_id=#{workId} and work_flow_id=#{workFlowId};")
    int turnWork(@Param("endTime")String endTime,@Param("workId")Long workId,@Param("workFlowId")Long workFlowId);


    /**
     * 移交时创建下个工作流程
     */
    @Insert("insert into work_flow (work_id, work_flow_id, company_id, host_id, turn_reason, start_time, state, work_flow_name) values (#{workId}, #{workFlowId}, #{companyId}, #{hostId}, #{turnReason}, #{startTime}, 0, #{workFlowName});")
    int createNexetWorkFlow(WorkFlow workFlow);

    /**
     * 查公司Id
     */
    @Select("select company_id from work_flow where work_id=#{workId} order by work_id asc limit 1")
    WorkFlow queryCompanyId(@Param("workId")Long workId);

    /**
     * 办结工作流程
     */
    @Update("update work_flow set end_time=#{endTime},state=1 where work_id=#{workId} and work_flow_id=#{workFlowId};")
    int finishWorkFlow(@Param("endTime")String endTime,@Param("workId")Long workId,@Param("workFlowId")Long workFlowId);

    /**
     * 撤回工作流程
     * 第一步，删除
     */
    @Delete("delete from work_flow where work_id=#{workId} and work_flow_id=#{workFlowId};")
    int deleteWorkFlow(@Param("workId")Long workId,@Param("workFlowId")Long workFlowId);

    /**
     * 撤回工作流程
     * 第二步，设置最大workFlowId的流程的state为0，删除删除时间
     */
    @Update("update work_flow set state=0, end_time=' ' where work_id=#{workId} and work_flow_id=#{workFlowId};")
    int updateWorkFlow(@Param("workId")Long workId, @Param("workFlowId")Long revokeWorkFlowId);

    /**
     * 查看工作流程说明
     */
    @Select("select turn_reason from work_flow where work_id=#{workId} and work_flow_id=#{workFlowId};")
    WorkFlow queryInformation(@Param("workId")Long workId, @Param("workFlowId")Long workFlowId);
}