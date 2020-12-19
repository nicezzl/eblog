package com.zzl.eblog.vo;

import com.zzl.eblog.entity.UserMessage;
import lombok.Data;

/**
 * @Author zzl
 * @Date 2020/12/18 17:38
 */
@Data
public class UserMessageVo extends UserMessage {

    private String toUserName;
    private String fromUserName;
    private String postTitle;
    private String commentContent;
}
