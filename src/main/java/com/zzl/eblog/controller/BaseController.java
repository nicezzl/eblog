package com.zzl.eblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzl.eblog.service.CommentService;
import com.zzl.eblog.service.PostService;
import com.zzl.eblog.service.UserService;
import com.zzl.eblog.shiro.AccountProfile;
import javafx.geometry.Pos;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
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
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    public Page getPage() {
        int pn = ServletRequestUtils.getIntParameter(request, "pn", 1);
        int size = ServletRequestUtils.getIntParameter(request, "size", 2);
        return new Page(pn, size);
    }

    public AccountProfile getProfile(){
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getProfileId() {
        return getProfile().getId();
    }

}
