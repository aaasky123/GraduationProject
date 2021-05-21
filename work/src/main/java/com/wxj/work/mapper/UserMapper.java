package com.wxj.work.mapper;

import com.wxj.work.entity.Company;
import com.wxj.work.entity.User;
import com.wxj.work.entity.UserExample;
import java.util.List;

import com.wxj.work.vo.userVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Long number);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Long number);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    @Select("SELECT * FROM user WHERE number=#{number}")
    User findUserByNumber(@Param("number") Long number);


    /**
     * 登录
     * @param user
     * @return
     */
    @Select("select number from user where number=#{number} and password=#{password}")
    Long login(User user);

    /**
     * 注册
     * @param user
     * @return
     */
    int registerUser(User user);
    //int insertSelective(User user);

    /**
     * 根据用户的公司id查询所在公司全员工
     * @param number
     * @return
     */
    List<User> queryCompanyUser(@Param("number") String number);

    ///**
    // * 根据number查询用户所属公司id
    // * @param number
    // * @return
    // */
    //User queryUserBelongsTo(@Param("number") String number);

    /**
     * 查看个人资料
     * @param number
     * @return
     */
    @Select("select realName, email, company_id as companyId, employee_id as employeeId, picture from user where `number`=#{number}")
    userVo queryUserInformation(@Param("number") String number);

    /**
     * 修改密码
     * @param number
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @Update("update user set password=#{newPassword} where number=#{number} and password=#{oldPassword}")
    int changePassword(@Param("number") String number, @Param("oldPassword") String oldPassword, @Param("newPassword") String newPassword);

    /**
     * 更新公司id
     */
    @Update("update user set company_id=#{companyId},employee_id=#{employeeId} where number=#{number};")
    int updateCompanyId(User user);

    /**
     * 查询公司最大雇员编号
     */
    @Select("select max(employee_id) from user where company_id=#{companyId}")
    int queryMaxEmployeeId(@Param("companyId") Integer companyId);

    /**
     * 更新用户信息
     */
    int updateInformation(@Param("realName") String realName, @Param("email") String email, @Param("number") Long number);

    /**
     * 更新忘记密码
     */
    @Update("update user set password=#{password} where number=#{number} and email=#{email} and realName=#{realname};")
    int updateForgetPassword(User user);

    /**
     * 登录
     */
    @Select("select number from user where number=#{number} and password=#{password}")
    Long adminLogin(@Param("number")Long number, @Param("password")String password);

    /**
     * 删除公司中用户
     */
    @Update("update user set company_id=null, employee_id=null where number=#{number};")
    int delCompanyId(User user);
}