package com.blog.www.service.impl;


import com.blog.www.mapper.CollectMapper;
import com.blog.www.model.Collect;
import com.blog.www.model.ResultCode;
import com.blog.www.model.User;
import com.blog.www.model.UserCollect;
import com.blog.www.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: chenyu
 * @date: 2019/11/16 20:13
 */
@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    CollectMapper collectMapper;

    @Override
    public boolean changeCollect(Collect collect) {
        return collectMapper.changeBlogCollect(collect) > 0;
    }

    @Override
    public boolean insertUserCollect(UserCollect userCollect) {
        return collectMapper.insertUserCollect(userCollect) > 0;
    }

    @Override
    public List<UserCollect> findUserCollect(User user) {
        return collectMapper.findUserCollect(user);
    }

    @Override
    public boolean deleteUserCollect(UserCollect userCollect) {
        collectMapper.deleteBlogCollect(userCollect);
        return collectMapper.deleteUserCollect(userCollect) >0;
    }

    @Override
    public boolean addCollectBlog(Collect collect) {
        return collectMapper.insertCollectBlog(collect)>0;
    }

    @Override
    public int changeToNormal(Collect collect, int userId) {
        if(collectMapper.deleteBlogByCIdAndBid(collect)==0){
            return ResultCode.BLOG_NOT_EXIT;
        }
        int DId=collectMapper.findNormalCollectByUId(userId);
        if(collectMapper.insertNormalCollect(DId,collect.getBlogId())>0){
            return ResultCode.SUCCESS;
        }else {
            return ResultCode.UNSPECIFIED;
        }
    }
}
