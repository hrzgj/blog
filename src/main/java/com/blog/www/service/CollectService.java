package com.blog.www.service;

import com.blog.www.model.Collect;
import com.blog.www.model.User;
import com.blog.www.model.UserCollect;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.blog.www.model.UserCollect;

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
      *添加博客到普通收藏夹中
      * @param collect 收藏夹关联表
      * @return 成功与否
      */
     boolean insertCollect(Collect collect);


     /**
      *添加博客到默认收藏夹中
      * @param collect 收藏夹关联表
      * @return 成功与否
      */
     boolean insertCollectToAuto(Collect collect);


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
     @Transactional(rollbackFor = Exception.class)
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
     @Transactional(rollbackFor = Exception.class)
     int changeToNormal(Collect collect,int userId);

     /**
      * 用户将一个博客从默认收藏夹移入非默认收藏夹
      * @param collect 非默认收藏夹
      * @param userId    用户id
      * @return     是否移入成功
      */
     @Transactional(rollbackFor = Exception.class)
     int changeNorToUnNormal(Collect collect,int userId);


     /**
      * 删除非默认收藏夹的一篇博客
      * @param collect 非默认收藏夹
      * @return     是否删除成功
      */
     boolean deleteCollectBlog(Collect collect);

     /**
      * 删除默认收藏夹的一篇博客
      * @param collect 收藏夹
      * @return 是否删除成功
      */
     boolean deleteNormalCollectBlog(Collect collect);

     /**
      * 更新非默认收藏夹名称和简介
      * @param userCollect 用户收藏夹
      * @return 是否更新成功
      */
     boolean updateCollectNameAndIntro(UserCollect userCollect);

     /**
      * 更新默认收藏夹名称
      * @param userCollect 收藏夹
      * @return 是否修改成功
      */
     boolean updateNormalCollectName(UserCollect userCollect);
}
