package com.zzl.eblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzl.eblog.entity.UserMessage;
import com.zzl.eblog.mapper.UserMessageMapper;
import com.zzl.eblog.service.UserMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-12-14
 */
@Service
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage> implements UserMessageService {

    @Autowired
    UserMessageMapper messageMapper;

    @Override
    public IPage paging(Page page, QueryWrapper<UserMessage> wrapper) {

        return messageMapper.selectMessages(page, wrapper);
    }
}
