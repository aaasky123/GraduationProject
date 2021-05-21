package com.wxj.work.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationsVo implements Serializable {
    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "createUserId")
    private Long createUserId;

    @JsonProperty(value = "notificationsContent")
    private String notificationsContent;

    @JsonProperty(value = "createTime")
    private Long createTime;

    @JsonProperty(value = "realname")
    private String realName;
}
