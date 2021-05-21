package com.wxj.work.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonTurrnUserVo {
    @JsonProperty(value = "id")
    private String id;
    @JsonProperty(value = "真实姓名")
    private String realName;
    @JsonProperty(value = "邮件")
    private String email;
    @JsonProperty(value = "公司id")
    private String companyId;
    @JsonProperty(value = "雇员id")
    private String employeeId;
    @JsonProperty(value = "用户id")
    private String userId;
    @JsonProperty(value = "常用移交人员id")
    private String turnUserId;


}
