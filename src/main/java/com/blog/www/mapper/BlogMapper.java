package com.blog.www.mapper;
import com.blog.www.model.Blog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author: chenyu
 * @date: 2019/10/29 21:32
 */
@Mapper
@Repository
public interface BlogMapper {


    @Insert("insert into blog(id,u_Id,content,time,title) values(#{blogId},#{author.id},#{content},#{title},#{date})")
//    @Results(value = {
//            @Result(property = "blogId", column = "id"),
//            @Result(property = "author", column = "u_id", one =@One("select * from user where id = id")),
//            @Result(property = "title", column = "title"),
//            @Result(property = "content", column = "content"),
//            @Result(property = "date", column = "time")
//    })
    boolean insertPassage(Blog blog);






}
