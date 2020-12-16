package com.zzl.eblog.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zzl.eblog.entity.Post;
import com.zzl.eblog.vo.CommentVo;
import com.zzl.eblog.vo.PostVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author zzl
 * @Date 2020/12/14 17:32
 */
@Controller
public class PostController extends  BaseController{

    @GetMapping("/category/{id:\\d*}")
    public String category(@PathVariable(name = "id") Long id){
        int pn = ServletRequestUtils.getIntParameter(request, "pn", 1);
        request.setAttribute("currentCategoryId",id);
        request.setAttribute("pn",pn);
        return "post/category";
    }

    @GetMapping("/post/{id:\\d*}")
    public String detail(@PathVariable(name = "id") Long id){

        PostVo postVo  = postService.selectOnePost(new QueryWrapper<Post>().eq("p.id",id));
        Assert.notNull(postVo,"文章已被删除");

        // 1.分页 2.文章id 3.用户id ，4.排序
        IPage<CommentVo> results = commentService.paging(getPage(), postVo.getId(), null,"created");

        request.setAttribute("currentCategoryId",postVo.getCategoryId());
        request.setAttribute("post",postVo);
        request.setAttribute("pageData",results);
        return "post/detail";
    }




}
