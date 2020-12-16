package com.zzl.eblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzl.eblog.service.CommentService;
import com.zzl.eblog.service.PostService;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author zzl
 * @Date 2020/12/14 21:40
 */

public class BaseController {

    @Autowired
    HttpServletRequest request;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    public Page getPage() {
        int pn = ServletRequestUtils.getIntParameter(request, "pn", 1);
        int size = ServletRequestUtils.getIntParameter(request, "size", 2);
        return new Page(pn, size);
    }

}
