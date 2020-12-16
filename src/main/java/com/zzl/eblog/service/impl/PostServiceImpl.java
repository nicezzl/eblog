package com.zzl.eblog.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzl.eblog.entity.Post;
import com.zzl.eblog.mapper.PostMapper;
import com.zzl.eblog.service.PostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzl.eblog.util.RedisUtil;
import com.zzl.eblog.vo.PostVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataUnit;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-12-14
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Autowired
    PostMapper postMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public IPage<PostVo> paging(Page page, Long categoryId, Long userId, Integer level, Boolean recommend, String order) {

        if(level == null) level = -1;

        QueryWrapper wrapper = new QueryWrapper<Post>()
                .eq(categoryId != null,  "category_id", categoryId)
                .eq(userId != null,  "user_id", userId)
                .eq(level == 0,  "level", 0)
                .gt(level > 0,  "level", 0)
                .orderByDesc(order != null, order);

        return postMapper.selectPosts(page, wrapper);
    }

    /**
     * 本周热议初始化
     */
    @Override
    public void initWeekRank() {

        //获取七天内的发表的文章
        List<Post> posts = this.list(new QueryWrapper<Post>()
                .gt("created", DateUtil.offsetDay(new Date(), -7))
                .select("id","id,title,user_id,comment_count, view_count, created")
        );

        //初始化文章的总阅读量
        for (Post post : posts) {
            String key = "day:rank:" + DateUtil.format((Date) post.getCreated(), DatePattern.PURE_DATE_FORMAT);

            redisUtil.zSet(key, post.getId(), post.getCommentCount());

            //七天后自动过期(例如：15号发表， 7-（18-15）=4)
            long between = DateUtil.between(new Date(), (Date) post.getCreated(), DateUnit.DAY);
            long expireTime =(7 - between) * 24 * 60 * 60;

            redisUtil.expire(key, expireTime);

            //缓存文章的一些基本信息
            this.hashCachePostIdAndTitle(post, expireTime);
        }
        //做并集
        this.zunionAndStoreLast7DayForWeekRank();
    }

    /**
     * 本周合并评论数量操作
     * 文章每日评论并集
     */
    private void zunionAndStoreLast7DayForWeekRank() {
        String currentDay = "day:rank" + DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT);
        String destkey = "week:rank";
        List<String> otherKeys = new ArrayList<>();
        for (int i = -6;i < 0; i++){
            String temp = "day:rank:" +
                    DateUtil.format(DateUtil.offsetDay(new Date(),i), DatePattern.PURE_DATE_FORMAT);

            otherKeys.add(temp);
        }

        redisUtil.zUnionAndStore(currentDay, otherKeys, destkey);

    }

    /**
     * 缓存文章的基本信息
     * @param post
     * @param expireTime
     */
    private void hashCachePostIdAndTitle(Post post, long expireTime) {
        String key = "rank:post:" + post.getId();
        boolean haskey = redisUtil.hasKey(key);
        if(!haskey){
            redisUtil.hset(key, "post.id", post.getId(), expireTime);
            redisUtil.hset(key, "post.title", post.getTitle(), expireTime);
            redisUtil.hset(key, "post.commentCount", post.getCommentCount(), expireTime);
//            redisUtil.hset(key, "post.viewCount", post.getViewCount(), expireTime);
        }
    }

    @Override
    public PostVo selectOnePost(QueryWrapper<Post> wrapper) {
        return postMapper.selectOnePost(wrapper);
    }


}
