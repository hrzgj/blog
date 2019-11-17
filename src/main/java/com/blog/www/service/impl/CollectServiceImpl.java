package com.blog.www.service.impl;

import com.blog.www.mapper.CollectMapper;
import com.blog.www.model.Collect;
import com.blog.www.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lyx
 * @date 2019/11/16 16:35
 */
@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    CollectMapper collectMapper ;

    @Override
    public boolean insertCollect(Collect collect) {
        if (collectMapper.insertCollect(collect)>0){
            return true;
        }
        return false;
    }

}
