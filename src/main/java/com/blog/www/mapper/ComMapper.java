package com.blog.www.mapper;

import com.blog.www.model.Blog;
import com.blog.www.model.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

/**
 * @author: chenyu
 * @date: 2019/11/16 11:54
 */
@Mapper
@Repository
public interface ComMapper {


    @Delete("delete from comment where b_id=#{id}")
    int deleteBlogCom(Blog blog);

    /**
     *@author chenyu
     *@date 22:56 2019/12/8
     *@param comment 内容
     *@return 增加条数
     **/
    @Insert("insert into comment (content,u_id,time,b_id) values (#{content},#{user},#{time},#{blog}) ")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insertComment(Comment comment);
}
