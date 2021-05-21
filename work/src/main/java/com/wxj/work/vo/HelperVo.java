package com.wxj.work.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HelperVo implements Serializable {
    @JsonProperty(value = "id")
    private Integer id;

    @JsonProperty(value = "真实姓名")
    private String realName;

    @JsonProperty(value = "协办人id")
    private Long helperUserId;

    @JsonProperty(value = "公司id")
    private Integer companyId;

    @JsonProperty(value = "工作id")
    private Long workId;

    @JsonProperty(value = "工作流程id")
    private Long workFlowId;
}
