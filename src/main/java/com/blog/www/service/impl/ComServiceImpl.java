package com.blog.www.service.impl;

import com.blog.www.mapper.ComMapper;
import com.blog.www.model.Comment;
import com.blog.www.service.ComService;
import com.blog.www.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: chenyu
 * @date: 2019/12/8 22:35
 */
@Service
public class ComServiceImpl implements ComService {

    @Autowired
    private ComMapper commMapper;

    @Override
    public boolean addComment(Comment comment) {
        comment.setTime(DateUtils.getDateToSecond());
        return commMapper.insertComment(comment)>0;
    }
}
