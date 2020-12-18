package com.zzl.eblog.shiro;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author zzl
 * @Date 2020/12/17 20:32
 */
@Data
public class AccountProfile implements Serializable {

    private Long id;

    private String username;
    private String email;
    private String sign;

    private String avatar;
    private Date created;

}
