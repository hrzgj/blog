package com.blog.www.service;

import com.blog.www.model.Collect;
import com.blog.www.model.User;
import com.blog.www.model.UserCollect;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: chenyu
 * @date: 2019/11/16 20:18
 */
public interface CollectService {

     /**
      * 用户修改一篇博客的收藏夹
      * @param collect 博客收藏夹的中间表
      * @param userId 用户id
      * @return 是否改变成功
      */
     @Transactional
     int changeCollect(Collect collect,int userId);

     /**
      * 登录用户新增一个收藏夹
      * @param userCollect 收藏夹
      * @return 是否插入成功
      */
     boolean insertUserCollect(UserCollect userCollect);

     /**
      * 登录用户查看自己的收藏夹
      * @param user 用户
      * @return 收藏夹的list
      */
     List<UserCollect> findUserCollect(User user);

     /**
      * 删除用户某个收藏夹,并删除该收藏夹的收藏博客记录
      * @param userCollect 用户收藏夹
      * @return  删除条数
      */
     @Transactional
     boolean deleteUserCollect(UserCollect userCollect);

     /**
      * 用户将某一个博客存入非默认收藏夹
      * @param collect 博客和收藏夹中间表
      * @param userId 用户id
      * @return  是否增加成功
      */
     int addCollectBlog(Collect collect,int userId);

     /**
      * 用户将一个博客从非默认收藏夹移入默认收藏夹
      * @param collect 非默认收藏夹
      * @param userId    用户id
      * @return     是否移入成功
      */
     @Transactional
     int changeToNormal(Collect collect,int userId);

}
