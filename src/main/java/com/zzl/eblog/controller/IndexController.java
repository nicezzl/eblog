package com.zzl.eblog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author zzl
 * @Date 2020/12/14 15:31
 */
@Controller
public class IndexController extends BaseController{
    @RequestMapping({"", "/", "index"})
    public String index() {

        // 1分页信息 2分类 3用户 4置顶  5精选 6排序
        IPage results = postService.paging(getPage(), null, null, null, null, "created");

        request.setAttribute("pageData", results);
        request.setAttribute("currentCategoryId", 0);
        return "index";
    }


}
