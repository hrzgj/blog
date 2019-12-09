package com.blog.www.service.impl;

import com.blog.www.mapper.BlogMapper;
import com.blog.www.mapper.ComMapper;
import com.blog.www.model.Comment;
import com.blog.www.model.Reply;
import com.blog.www.model.ResultCode;
import com.blog.www.service.ComService;
import com.blog.www.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: chenyu
 * @date: 2019/12/8 22:35
 */
@Service
public class ComServiceImpl implements ComService {

    @Autowired
    private ComMapper commMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public int addComment(Comment comment) {
        comment.setTime(DateUtils.getDateToSecond());
        if(blogMapper.findBlogById(comment.getBlog())<=0){
            return ResultCode.BLOG_NOT_EXIT;
        }
        if(commMapper.insertComment(comment)>0){
            return ResultCode.SUCCESS;
        }
        else {
            return ResultCode.UNSPECIFIED;
        }
    }

    @Override
    public int replyComment(Reply reply) {
        reply.setTime(DateUtils.getDateToSecond());
        if(blogMapper.findBlogById(reply.getBlogId())<=0){
            return ResultCode.BLOG_NOT_EXIT;
        }
        if(commMapper.findCommentById(reply.getCommentId())<=0){
            return ResultCode.COMMENT_NO_EXIT;
        }
        if(commMapper.insertReply(reply)>0){
            return ResultCode.SUCCESS;
        }
        else {
            return ResultCode.UNSPECIFIED;
        }
    }

    @Override
    public List<Comment> getCommentsByBlogId(Integer blogId) {
        if (blogId != null){
            if (blogMapper.findBlogById(blogId)<=0){
                return null;
            }
            if (commMapper.getCommentCountByBlogId(blogId)!=0){
                List<Comment> comments = commMapper.getCommentsByBlogId(blogId);
                List<Reply> replies = new ArrayList<Reply>();
                for (Comment comment:comments) {
                    if (commMapper.getReplyCountByBlogId(comment.getId())!=0){
                        replies.add((Reply) commMapper.getRepliesByCommentId(comment.getId()));
                    }
                }
            }
        }
        return null;
    }
}
