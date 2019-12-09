package com.blog.www.mapper;

import com.blog.www.model.Blog;
import com.blog.www.model.Comment;
import com.blog.www.model.Reply;
import com.blog.www.model.User;
import lombok.Data;
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
     * 新增评论
     *@author chenyu
     *@date 22:56 2019/12/8
     *@param comment 内容
     *@return 增加条数
     **/
    @Insert("insert into comment (content,u_id,time,b_id) values (#{content},#{commenter.id},#{time},#{blog})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insertComment(Comment comment);

    /**
     * 新增回复
     *@author chenyu
     *@date 22:10 2019/12/9
     *@param reply 回复
     *@return  增加条数
     **/
    @Insert("insert into s_comment (content,r_id,br_id,time,c_id) values(#{content},#{reply.id},#{beReply.id},#{time},#{commentId})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insertReply(Reply reply);

    /**
     * 根据评论id查询是否有该评论
     *@author chenyu
     *@date 22:10 2019/12/9
     *@param commentId 回复id
     *@return 查看条数
     **/
    @Select("select count(1) from comment where id=#{commentId}")
    int findCommentById(int commentId);

    /**
     * 根据评论id删除评论
     *@author chenyu
     *@date 22:11 2019/12/9
     *@param commentId 评论id
     *@return 删除多少
     **/
    @Delete("delete from comment where id=#{commentId}")
    int deleteComment(int commentId);

    /**
     * 根据评论id删除评论下的所有回复
     *@author chenyu
     *@date 22:12 2019/12/9
     *@param commentId 评论id
     *@return int
     **/
    @Delete("delete from s_comment where c_id=#{commentId}")
    int deleteRelyByCommentId(int commentId);

    /**
     * 根据回复id删除回复
     *@author chenyu
     *@date 22:12 2019/12/9
     *@param replyId 回复id
     *@return int
     **/
    @Delete("delete from s_comment where id=#{replyId}")
    int deleteRelyById(int replyId);

    /**
     * 根据博客id搜索博客下的所有评论id
     *@author chenyu
     *@date 22:13 2019/12/9
     *@param blogId 博客id
     *@return 评论id集合
     **/
    @Select("select id from comment where b_id=#{blogId}")
    List<Integer> findCommentIdByBlogId(int blogId);

    /**
     * 将所有评论下的所有回复删除
     *@author chenyu
     *@date 22:14 2019/12/9
     *@param list 评论id集合
     *@return int
     **/
    @DeleteProvider(type = Provider.class,method = "deleteRelyByListCommentId")
    int deleteRelyByListCommentId(List<Integer> list);


    class Provider{
        public String deleteRelyByListCommentId(List<Integer> list){
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append("delete from s_comment where c_id in (");
            for(int i=0;i<list.size();i++){
                stringBuilder.append(list.get(i));
                if(i<list.size()-1){
                    stringBuilder.append(",");
                }
            }
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }
}
