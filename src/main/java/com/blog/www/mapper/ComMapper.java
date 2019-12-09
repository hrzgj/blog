package com.blog.www.mapper;

import com.blog.www.model.Blog;
import com.blog.www.model.Comment;
import com.blog.www.model.Reply;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author: chenyu
 * @date: 2019/11/16 11:54
 */
@Mapper
@Repository
public interface ComMapper {

    /**
     * 删除博客下的所有评论
     *@author chenyu
     *@date 20:04 2019/12/9
     *@param blog 博客
     *@return 删除评论
     **/
    @Delete("delete from comment where b_id=#{id}")
    int deleteBlogCom(Blog blog);

    /**
     * 新增回复
     *@author chenyu
     *@date 22:56 2019/12/8
     *@param comment 内容
     *@return 增加条数
     **/
    @Insert("insert into comment (content,u_id,time,b_id) values (#{content},#{user},#{time},#{blog})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insertComment(Comment comment);

    @Insert("insert into s_comment (content,r_id,br_id,time,b_id) values(#{content},#{replyId},#{beReplyId},#{time},#{blogId})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insertReply(Reply reply);

    @Select("select count(1) from comment where id=#{commentId}")
    int findCommentById(int commentId);
}
