package com.zzl.eblog.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zzl.eblog.common.lang.Result;
import com.zzl.eblog.entity.Post;
import com.zzl.eblog.entity.UserCollection;
import com.zzl.eblog.util.ValidationUtil;
import com.zzl.eblog.vo.CommentVo;
import com.zzl.eblog.vo.PostVo;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

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

//        postService.putViewCount(postVo);

        // 1.分页 2.文章id 3.用户id ，4.排序
        IPage<CommentVo> results = commentService.paging(getPage(), postVo.getId(), null,"created");

        request.setAttribute("currentCategoryId",postVo.getCategoryId());
        request.setAttribute("post",postVo);
        request.setAttribute("pageData",results);
        return "post/detail";
    }

    //判断用户是否收藏了文章
    @ResponseBody
    @PostMapping("/collection/find/")
    public Result collectionFind(Long pid){

        int count = collectionService.count(new QueryWrapper<UserCollection>()
                .eq("user.id",getProfileId())
                .eq("post.id",pid)
        );

        return Result.success(MapUtil.of("collection", count > 0));
    }

    @ResponseBody
    @PostMapping("/collection/add/")
    public Result collectionAdd(Long pid){

        Post post = postService.getById(pid);

        Assert.isTrue(post != null,"该帖子已被删除");

        int count = collectionService.count(new QueryWrapper<UserCollection>()
                .eq("user.id",getProfileId())
                .eq("post.id",pid)
        );
        if (count > 0){
            return Result.fail("你已收藏");
        }

        UserCollection collection = new UserCollection();
        collection.setUserId(getProfileId());
        collection.setPostId(pid);
        collection.setCreated(new Date());
        collection.setModified(new Date());
        collection.setPostUserId(post.getUserId());

        collectionService.save(collection);

        return Result.success();
    }

    @ResponseBody
    @PostMapping("/collection/remove/")
    public Result collectionRemove(Long pid) {
        Post post = postService.getById(pid);

        Assert.isTrue(post != null,"该帖子已被删除");

        collectionService.remove(new QueryWrapper<UserCollection>()
                .eq("user.id",getProfileId())
                .eq("post.id",pid)
        );

        return Result.success();
    }

    @GetMapping("/post/edit")
    public String edit(){
        String id = request.getParameter("id");
        if(!StringUtils.isEmpty(id)) {
            Post post = postService.getById(id);
            Assert.isTrue(post != null, "改帖子已被删除");
            Assert.isTrue(post.getUserId().longValue() == getProfileId().longValue(), "没权限操作此文章");
            request.setAttribute("post", post);
        }
        request.setAttribute("categories", categoryService.list());
        return "/post/edit";
    }

    @ResponseBody
    @PostMapping("/post/submit")
    public Result submit(Post post){

        ValidationUtil.ValidResult validResult = ValidationUtil.validateBean(post);
        if(validResult.hasErrors()) {
            return Result.fail(validResult.getErrors());
        }

        if(post.getId() == null) {
            post.setUserId(getProfileId());

            post.setModified(new Date());
            post.setCreated(new Date());
            post.setCommentCount(0);
            post.setEditMode(null);
            post.setLevel(0);
            post.setRecommend(false);
            post.setViewCount(0);
            post.setVoteDown(0);
            post.setVoteUp(0);
            postService.save(post);

        } else {
            Post tempPost = postService.getById(post.getId());
            Assert.isTrue(tempPost.getUserId().longValue() == getProfileId().longValue(), "无权限编辑此文章！");

            tempPost.setTitle(post.getTitle());
            tempPost.setContent(post.getContent());
            tempPost.setCategoryId(post.getCategoryId());
            postService.updateById(tempPost);
        }

        return Result.success().action("/post/" + post.getId());
    }


    @ResponseBody
    @Transactional
    @PostMapping("/post/delete")
    public Result delete(Long id) {
        Post post = postService.getById(id);

        Assert.notNull(post, "该帖子已被删除");
        Assert.isTrue(post.getUserId().longValue() == getProfileId().longValue(), "无权限删除此文章！");

        postService.removeById(id);

        // 删除相关消息、收藏等
        messageService.removeByMap(MapUtil.of("post_id", id));
        collectionService.removeByMap(MapUtil.of("post_id", id));


        return Result.success().action("/user/index");
    }




}
