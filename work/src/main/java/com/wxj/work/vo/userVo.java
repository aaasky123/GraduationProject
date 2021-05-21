package com.wxj.work.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class userVo implements Serializable {
    @JsonProperty(value = "真实姓名")
    private String realName;
    @JsonProperty(value = "邮件")
    private String email;
    @JsonProperty(value = "公司id")
    private String companyId;
    @JsonProperty(value = "雇员id")
    private String employeeId;
    @JsonProperty(value = "头像")
    private String picture;
}
