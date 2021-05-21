package com.wxj.work.mapper;

import java.util.List;

import com.wxj.work.entity.WorkManage;
import com.wxj.work.entity.WorkManageExample;
import org.apache.ibatis.annotations.*;

@Mapper
public interface WorkManageMapper {
    long countByExample(WorkManageExample example);

    int deleteByExample(WorkManageExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WorkManage record);

    int insertSelective(WorkManage record);

    List<WorkManage> selectByExample(WorkManageExample example);

    WorkManage selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WorkManage record, @Param("example") WorkManageExample example);

    int updateByExample(@Param("record") WorkManage record, @Param("example") WorkManageExample example);

    int updateByPrimaryKeySelective(WorkManage record);

    int updateByPrimaryKey(WorkManage record);

    /**
     * 查询公司人员管理
     */
    List<WorkManage> queryManage(WorkManage workManage);

    /**
     * 添加人员管理
     */
    @Insert("insert into work_manage values(0, #{userId}, #{manageId}, now(), #{companyId});")
    int addManage(WorkManage workManage);

    /**
     * 修改人员管理
     */
    @Update("update work_manage set manage_id=#{manageId} where id=#{id};")
    int editManage(WorkManage workManage);

    /**
     * 删除人员管理
     */
    @Delete("delete from work_manage where id=#{id};")
    int delManage(WorkManage workManage);
}