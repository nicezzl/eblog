package com.zzl.eblog.vo;

import com.zzl.eblog.entity.Post;
import lombok.Data;

import javax.swing.*;

/**
 * @Author zzl
 * @Date 2020/12/15 10:41
 */
@Data
public class PostVo extends Post {
    private Long authorId;
    private String authorName;
    private String authorAvatar;

    private String categoryName;

}
