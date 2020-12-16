package com.zzl.eblog.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzl.eblog.entity.Category;
import com.zzl.eblog.service.CategoryService;
import com.zzl.eblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * @Author zzl
 * @Date 2020/12/15 9:13
 */
@Component
public class ContextStartup implements ApplicationRunner, ServletContextAware {

    @Autowired
    CategoryService categoryService;

    ServletContext servletContext;

    @Autowired
    PostService postService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<Category> categoryList = categoryService.list(new QueryWrapper<Category>()
            .eq("status",0)
        );

        servletContext.setAttribute("categoryList",categoryList);

//        postService.initWeekRank();
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
