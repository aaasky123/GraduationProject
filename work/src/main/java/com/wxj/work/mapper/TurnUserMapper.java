package com.wxj.work.mapper;

import com.wxj.work.entity.TurnUser;
import com.wxj.work.entity.TurnUserExample;

import java.util.HashMap;
import java.util.List;

import com.wxj.work.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TurnUserMapper {
    long countByExample(TurnUserExample example);

    int deleteByExample(TurnUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TurnUser record);

    int insertSelective(TurnUser record);

    List<TurnUser> selectByExample(TurnUserExample example);

    TurnUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TurnUser record, @Param("example") TurnUserExample example);

    int updateByExample(@Param("record") TurnUser record, @Param("example") TurnUserExample example);

    int updateByPrimaryKeySelective(TurnUser record);

    int updateByPrimaryKey(TurnUser record);

    /**
     * 查找用户的常用移交人列表
     * @param number
     * @return
     */
    List<User> queryCommonTurnUserList(@Param("number")String number);

    /**
     * 查询是否已经添加常用移交人
     */
    List<TurnUser> queryisCTUinList(TurnUser turnUser);

    /**
     *添加常用移交人
     */
    int insertCommonTUrnUser(TurnUser turnUser);

    /**
     * 删除常用移交人
     */
    int deleteCommonTurnUser(TurnUser turnUser);
}