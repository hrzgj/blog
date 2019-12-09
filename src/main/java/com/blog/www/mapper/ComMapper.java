package com.blog.www.mapper;

import com.blog.www.model.Blog;
import com.blog.www.model.Comment;
import com.blog.www.model.Reply;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * 查询该博客的评论条数
     * @param blogId 博客id
     * @return 数据条数
     */
    @Select("select count(1) from comment where b_id = #{blogId}")
    Integer getCommentCountByBlogId(int blogId);

    /**
     * 查询该评论的子评论条数
     * @param commentId 评论id
     * @return 数据条数
     */
    @Select("select count(1) from s_comment where c_id = #{commentId}")
    Integer getReplyCountByBlogId(int commentId);

    /**
     * 根据博客id查找该博客下的所有评论
     * @param blogId 博客id
     * @return 评论列表
     */
    @Results(value = {
            @Result(property = "id",column = "id"),
            @Result(property = "content",column = "content"),
            @Result(property = "commenter",column = "u_id",one = @One(select = "com.blog.www.mapper.UserMapper.findUserById")),
            @Result(property = "blog",column = "b_id"),
            @Result(property = "time",column = "time")
         }
    )
    @Select("select * from comment where b_id = #{blogId}")
    List<Comment> getCommentsByBlogId(int blogId);


    /**
     *根据评论id获取该评论下的回复
     * @param commentId 评论id
     * @return 评论下的子评论列表
     */
    @Results(value = {
            @Result(property = "id",column = "id"),
            @Result(property = "content",column = "content"),
            @Result(property = "reply",column = "r_id",one = @One(select = "com.blog.www.mapper.UserMapper.findUserById")),
            @Result(property = "beReply",column = "br_id",one = @One(select = "com.blog.www.mapper.UserMapper.findUserById")),
            @Result(property = "time",column = "time"),
            @Result(property = "commentId",column = "c_id")
    }
    )
    @Select("select * from s_comment where c_id = #{commentId}")
    List<Reply> getRepliesByCommentId(int commentId);

}
