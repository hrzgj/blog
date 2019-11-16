package com.blog.www.service;

import com.blog.www.model.Collect;
import com.blog.www.model.UserCollect;

/**
 * @author: chenyu
 * @date: 2019/11/16 20:18
 */
public interface CollectService {

     /**
      * 用户修改一篇博客的收藏夹
      * @param collect 博客收藏夹的中间表
      * @return 是否改变成功
      */
     boolean changeCollect(Collect collect);

     /**
      * 登录用户新增一个收藏夹
      * @param userCollect 收藏夹
      * @return 是否插入成功
      */
     boolean insertUserCollect(UserCollect userCollect);
}
