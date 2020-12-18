package com.zzl.eblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.support.odps.udf.CodecCheck;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzl.eblog.common.lang.Result;
import com.zzl.eblog.entity.User;
import com.zzl.eblog.mapper.UserMapper;
import com.zzl.eblog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzl.eblog.shiro.AccountProfile;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-12-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Result register(User user) {
        int count = this.count(new QueryWrapper<User>()
            .eq("email", user.getEmail())
            .or()
            .eq("username",user.getUsername())
        );
        if(count > 0) return Result.fail("用户名或邮箱已被占用!");

        User temp = new User();
        temp.setUsername(user.getUsername());
        //md5加密
        temp.setPassword(SecureUtil.md5(user.getPassword()));
        temp.setEmail(user.getEmail());
        temp.setAvatar("/res/images/avatar/default.png");

        temp.setCreated(new Date());
        temp.setPoint(0);
        temp.setVipLevel(0);
        temp.setCommentCount(0);
        temp.setPostCount(0);
        temp.setGender("0");
        this.save(temp);

        return Result.success();
    }

    @Override
    public AccountProfile login(String email, String password) {


        User user = this.getOne(new QueryWrapper<User>().eq("email",email));

        if (user == null){
            throw new UnknownAccountException();
        }
        if (!user.getPassword().equals(password)) {
            throw  new IncorrectCredentialsException();
        }

        user.setLasted(new Date());
        this.updateById(user);

        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(user, profile);

        return profile;
    }
}
