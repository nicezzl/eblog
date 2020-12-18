package com.zzl.eblog.service;

import com.zzl.eblog.common.lang.Result;
import com.zzl.eblog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzl.eblog.shiro.AccountProfile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-12-14
 */
public interface UserService extends IService<User> {

    Result register(User user);

    AccountProfile login(String email, String password);
}
