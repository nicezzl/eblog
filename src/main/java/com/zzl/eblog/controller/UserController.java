package com.zzl.eblog.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzl.eblog.common.lang.Consts;
import com.zzl.eblog.common.lang.Result;
import com.zzl.eblog.entity.Post;
import com.zzl.eblog.entity.User;
import com.zzl.eblog.service.UserService;
import com.zzl.eblog.shiro.AccountProfile;
import com.zzl.eblog.util.UploadUtil;
import org.apache.shiro.authc.Account;
import org.beetl.ext.fn.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.geom.RectangularShape;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Author zzl
 * @Date 2020/12/17 21:16
 */
@Controller
public class UserController extends BaseController {

    @Autowired
    UploadUtil uploadUtil;

    @GetMapping("/user/home")
    public String home(){

        User user = userService.getById(getProfileId());

        List<Post> posts = postService.list(new QueryWrapper<Post>()
            .eq("user_id", getProfileId())
            .orderByDesc("created")
        );
        request.setAttribute("user",user);
        request.setAttribute("posts",posts);

        return "/user/home";
    }


    @GetMapping("/user/set")
    public String set(){
        User user = userService.getById(getProfileId());
        request.setAttribute("user",user);

        return "/user/set";
    }

    @ResponseBody
    @PostMapping("/user/set")
    public Result doSet(User user){

        if(StrUtil.isNotBlank(user.getAvatar())){
            User temp = userService.getById(getProfileId());
            temp.setAvatar(user.getAvatar());
            userService.updateById(temp);

            AccountProfile profile = getProfile();
            profile.setAvatar(user.getAvatar());

            return Result.success().action("/user/set#avatar");

        }


        if(StrUtil.isBlank(user.getUsername())){
            return Result.fail("昵称不能为空");
        }
        int count =userService.count(new QueryWrapper<User>()
                .eq("username", getProfile().getUsername())
                .ne("id", getProfileId())
        );
        if (count > 0){
            return Result.fail("昵称已被占用");
        }

        User temp = userService.getById(getProfileId());
        temp.setUsername(user.getUsername());
        temp.setGender(user.getGender());
        temp.setSign(user.getSign());
        userService.updateById(temp);

        AccountProfile profile = getProfile();
        profile.setUsername(temp.getUsername());
        profile.setSign(temp.getSign());

        return Result.success().action("/user/set#info");
    }

    @ResponseBody
    @PostMapping("/user/upload")
    public Result uploadAvatar(@RequestParam(name = "file") MultipartFile file) throws IOException {


        return uploadUtil.upload(UploadUtil.type_avatar,file);
    }

    @ResponseBody
    @PostMapping("/user/repass")
    public Result repass(String nowpass, String pass, String repass) {
        if(!pass.equals(repass)) {
            return Result.fail("两次密码不相同");
        }

        User user = userService.getById(getProfileId());

        String nowPassMd5 = SecureUtil.md5(nowpass);
        if(!nowPassMd5.equals(user.getPassword())) {
            return Result.fail("密码不正确");
        }

        user.setPassword(SecureUtil.md5(pass));
        userService.updateById(user);

        return Result.success().action("/user/set#pass");

    }

}
