package com.blog.www.service.impl;


import com.blog.www.controller.CollectControl;
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
<<<<<<< HEAD
 * @author lyx
 * @date 2019/11/16 16:35
=======
 * @author: chenyu
 * @date: 2019/11/16 20:13
>>>>>>> 263a0bb915e25c9230ff256db8733c0ecfb3504e
 */
@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    CollectMapper collectMapper;

    @Override
    public int changeCollect(Collect collect,int oldCollectId,int userId) {
        if(collectMapper.findCollectByUserId(userId,oldCollectId,collect.getUserCollectId())!=2){
            return ResultCode.COLLECT_ERROR;
        }
        //查询用户某个非默认收藏夹内是否存在这篇博客
        //收藏夹id
        int id=collectMapper.findCollectExitBlogByUId(oldCollectId,collect);
        if(id<=0){
            return ResultCode.BLOG_NOT_EXIT;
        }
        collect.setId(id);
        //查询用户要移入的收藏夹内是否存在该博客
        if(collectMapper.findCollectExitBlog(collect)!=0){
            return ResultCode.BLOG_EXIT;
        }
        //改变该博客的收藏夹
        if(collectMapper.changeBlogCollect(collect) > 0){
            return ResultCode.SUCCESS;
        }else {
            return ResultCode.UNSPECIFIED;
        }
    }

    @Override
    public boolean insertUserCollect(UserCollect userCollect) {
        return collectMapper.insertUserCollect(userCollect) > 0;
    }


    @Override
    public boolean insertCollect(Collect collect) {
        if (collectMapper.insertCollect(collect)>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean insertCollectToAuto(Collect collect) {
        //如果数据库中没有此条博客存入默认收藏夹的消息，则继续操作
        if (collectMapper.selectBlogIsAuto(collect.getBlogId(),collect.getUserCollectId()) == 0){
            if (collectMapper.insertCollectToAuto(collect)>0){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<UserCollect> findUserCollect(User user) {
        return collectMapper.findUserCollect(user);
    }

    @Override
    public boolean deleteUserCollect(UserCollect userCollect) {
        //删除收藏夹下的博客记录
        collectMapper.deleteBlogCollect(userCollect);
        //删除收藏夹
        return collectMapper.deleteUserCollect(userCollect) >0;
    }

    @Override
    public int addCollectBlog(Collect collect,int userId) {
        //查看非默认收藏夹是否存在这篇博客
        if(collectMapper.findCollectExitBlog(collect)>0){
            return ResultCode.BLOG_EXIT;
        }
        //查看该收藏夹是否属于该用户
        if(collectMapper.checkUserCollect(collect,userId)<=0){
            return ResultCode.RIGHT_ERROR;
        }
        try {
            //插入默认收藏夹这篇博客
            if(collectMapper.insertCollectBlog(collect)>0){
                return ResultCode.SUCCESS;
            }
        }catch (Exception e) {
            return ResultCode.UNSPECIFIED;
        }
        return  ResultCode.UNSPECIFIED;
    }

    @Override
    public int changeToNormal(Collect collect, int userId) {
        //获取用户默认收藏夹id
        int DId=collectMapper.findNormalCollectByUId(userId);
        //查看默认收藏夹是否有该博客
        if(collectMapper.findNorMalCollectExitBlog(DId,userId)>0){
            return ResultCode.BLOG_EXIT;
        }
        //删除非默认收藏夹里的博客，若删除失败即不存在该博客
        if(collectMapper.deleteBlogByCIdAndBid(collect)==0){
            return ResultCode.BLOG_NOT_EXIT;
        }
        //插入默认收藏夹
        if(collectMapper.insertNormalCollect(DId,collect.getBlogId())>0){
            return ResultCode.SUCCESS;
        }else {
            return ResultCode.UNSPECIFIED;
        }
    }

    @Override
    public boolean deleteCollectBlog(Collect collect){
        return collectMapper.deleteCollectBlog(collect)>0;
    }

    @Override
    public boolean deleteNormalCollectBlog(Collect collect) {
        return  collectMapper.deleteNormalCollectBlog(collect)>0;
    }

    @Override
    public boolean updateCollectNameAndIntro(UserCollect userCollect) {
        return collectMapper.updateCollectNameAndIntro(userCollect)>0;

    }

    @Override
    public boolean updateNormalCollectName(UserCollect userCollect) {
        return collectMapper.updateNormalCollectName(userCollect)>0;
    }

    @Override
    public int changeNorToUnNormal(Collect collect, int userId) {


        //获取用户默认收藏夹id
        int DId=collectMapper.selectAuto(userId);
        //查看默认收藏夹是否有该博客
        if(collectMapper.findNorMalCollectExitBlog(DId,collect.getBlogId())==0){
            //默认收藏夹中没有该博客
            return ResultCode.BLOG_NOT_EXIT;
        }
        //非默认收藏夹已有该博客
        else if(collectMapper.findCollectExitBlog(collect)!=0) {
            return ResultCode.BLOG_EXIT;
        }
        else {
                //默认收藏夹中存在此博客，进行插入新的收藏夹和删除默认收藏夹中博客的操作
                if (collectMapper.insertCollect(collect)>0 && collectMapper.deleteEditCollect(collect.getBlogId(),DId)>0){
                    return ResultCode.SUCCESS;
                }else{
                    return ResultCode.UNSPECIFIED;
                }
            }
    }
}



