package com.zzl.eblog.template;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzl.eblog.common.templates.DirectiveHandler;
import com.zzl.eblog.common.templates.TemplateDirective;
import com.zzl.eblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author zzl
 * @Date 2020/12/15 21:21
 */
@Component
public class PostsTemplate extends TemplateDirective {

    @Autowired
    PostService postService;

    @Override
    public String getName() {
        return "posts";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {

        Integer level = handler.getInteger("level");
        Integer pn = handler.getInteger("pn",1);
        Integer size = handler.getInteger("size",2);
        Long categoryId = handler.getLong("categoryId");

        IPage page = postService.paging(new Page(pn,size),categoryId,null,level,null,"created");

        handler.put(RESULTS,page).render();
    }
}
