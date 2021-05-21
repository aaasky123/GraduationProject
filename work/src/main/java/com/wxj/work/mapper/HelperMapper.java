package com.wxj.work.mapper;

import com.wxj.work.entity.Helper;
import com.wxj.work.entity.HelperExample;
import java.util.List;

import com.wxj.work.vo.HelperVo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface HelperMapper {
    long countByExample(HelperExample example);

    int deleteByExample(HelperExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Helper record);

    int insertSelective(Helper record);

    List<Helper> selectByExample(HelperExample example);

    Helper selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Helper record, @Param("example") HelperExample example);

    int updateByExample(@Param("record") Helper record, @Param("example") HelperExample example);

    int updateByPrimaryKeySelective(Helper record);

    int updateByPrimaryKey(Helper record);

    /**
     * 新增协办人
     */
    @Insert("insert into helper (id, helper_user_id, work_id, work_flow_id, company_id) values (0, #{helperUserId}, #{workId}, #{workFlowId}, #{companyId});")
    int addHelper(Helper helper);

    /**
     * 查询协办人
     */
    @Select("select helper.*, realName from helper left join user on helper.helper_user_id=user.number where work_id=#{workId} and work_flow_id=#{workFlowId};")
    List<HelperVo> queryHelper(Helper helper);

    /**
     * 删除协办人
     */
    @Delete("delete from helper where id=#{id};")
    int deleteHelper(Integer id);
}