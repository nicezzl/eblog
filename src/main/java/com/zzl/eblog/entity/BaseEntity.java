package com.zzl.eblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @Author zzl
 * @Date 2020/12/14 21:39
 */
@Data
public class BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Date created;
    private Date modified;

}
