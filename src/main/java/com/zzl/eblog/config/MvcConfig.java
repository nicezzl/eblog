package com.zzl.eblog.config;

import com.zzl.eblog.common.lang.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author zzl
 * @Date 2020/12/18 12:44
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    Consts consts;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/upload/avatar/**")
                .addResourceLocations("file:///" + consts.getUploadDir() + "/avatar/");

    }


}
