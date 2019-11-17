package com.blog.www.service;

import com.blog.www.model.Collect;

/**
 * @author lyx
 * @date 2019/11/16 16:32
 */
public interface CollectService {

    /**
     * 将博客加到收藏夹中
     * @param collect 集合
     * @return 成功与否
     */
    boolean insertCollect(Collect collect);


}
