package com.zzl.eblog.config;

import com.zzl.eblog.template.HotsTemplate;
import com.zzl.eblog.template.PostsTemplate;
import com.zzl.eblog.template.TimeAgoMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author zzl
 * @Date 2020/12/15 21:18
 */
@Component
public class FreemarkerConfig {

    @Autowired
    private freemarker.template.Configuration configuration;

    @Autowired
    private PostsTemplate postsTemplate;

    @Autowired
    private HotsTemplate hotsTemplate;
//
//    @Autowired
//    HotsTemplate hotsTemplate;

    @PostConstruct
    public void setUp() {
        configuration.setSharedVariable("timeAgo", new TimeAgoMethod());
        configuration.setSharedVariable("posts", postsTemplate);
        configuration.setSharedVariable("hots", hotsTemplate);
//        configuration.setSharedVariable("shiro", new ShiroTags());
    }

}
