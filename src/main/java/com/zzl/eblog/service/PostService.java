package com.zzl.eblog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzl.eblog.entity.Post;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzl.eblog.vo.PostVo;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-12-14
 */

public interface PostService extends IService<Post> {

    IPage paging(Page page, Long categoryId, Long userId, Integer level, Boolean recommend, String order);

    PostVo selectOnePost(QueryWrapper<Post> wrapper);

    void initWeekRanks();

    void incrCommentCountAndUnitForWeekRank(long postId, boolean isIncr);

    void putViewCount(PostVo postVo);
}
