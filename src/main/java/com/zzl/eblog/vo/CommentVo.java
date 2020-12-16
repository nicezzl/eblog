package com.zzl.eblog.vo;

import com.zzl.eblog.entity.Comment;
import lombok.Data;

/**
 * @Author zzl
 * @Date 2020/12/16 10:51
 */
@Data
public class CommentVo extends Comment {

    private Long authorId;
    private String authorName;
    private String authorAvatar;

}
