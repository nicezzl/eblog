package com.zzl.eblog.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Author zzl
 * @Date 2020/12/17 16:24
 */
@Controller
public class AuthController extends BaseController {

    @Autowired
    Producer producer;

    @GetMapping("/capthca.jpg")
    public void kaptcha(HttpServletResponse response) throws IOException {

        //验证码
        String text = producer.createText();
        BufferedImage image = producer.createImage(text);

        response.setHeader("Cache-Control","no-store,no-cache");
        response.setContentType("image/jpeg");
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image,"jpg",outputStream);

    }


    @GetMapping("/login")
    public String login() {
        return "/auth/login";
    }

    @GetMapping("/register")
    public String register(){
        return "/auth/reg";
    }

}
