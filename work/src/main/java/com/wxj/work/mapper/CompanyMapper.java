package com.wxj.work.mapper;

import com.wxj.work.entity.Company;
import com.wxj.work.entity.CompanyExample;
import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface CompanyMapper {
    long countByExample(CompanyExample example);

    int deleteByExample(CompanyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Company record);

    int insertSelective(Company record);

    List<Company> selectByExample(CompanyExample example);

    Company selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Company record, @Param("example") CompanyExample example);

    int updateByExample(@Param("record") Company record, @Param("example") CompanyExample example);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);

    /**
     * 注册公司
     */
    @Insert("insert into company values(0,#{companyId},#{companyName},#{adminId});")
    int registerCompany(Company company);

    /**
     * 查询是否是管理员
     */
    @Select("select * from company where admin_id=#{number} and company_id=#{companyId};")
    Company queryAdmin(@Param("number") Long number, @Param("companyId") Long companyId);

    /**
     * 查询公司管理员
     */
    @Select("select id,admin_id from company where company_id=#{companyId};")
    List<Company> queryCompanyAdmin(Company company);

    /**
     * 添加管理员
     */
    @Insert("insert into company values (0, #{companyId}, #{companyName}, #{adminId});")
    int addAdmin(Company company);

    /**
     * 删除管理员
     */
    @Delete("delete from company where id=#{id};")
    int delAdmin(Company company);

    /**
     * 根据公司id查询
     */
    @Select("select * from company where company_id=#{companyId}")
    List<Company> selectCompanyById(Company company);

    /**
     * 根据公司名查询
     */
    @Select("select * from company where company_name=#{companyName}")
    List<Company> selectCompanyByName(Company company);
}