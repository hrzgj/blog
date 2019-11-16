package com.blog.www.mapper;
import com.blog.www.model.Blog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import com.blog.www.model.Blog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author: chenyu
 * @date: 2019/10/29 21:32
 */
@Mapper
@Repository
public interface BlogMapper {


    @Insert("insert into blog(id,u_id,content,title,time) values(#{blogId},#{author.id},#{content},#{title},#{date})")
    @Results(value = {
            @Result(property = "blogId", column = "id"),
            @Result(property = "author", column = "u_id", one =@One(select = "com.blog.www.mapper.UserMapper.findUserById")),
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            @Result(property = "date", column = "time")
    })
    int insertPassage(Blog blog);


    @Delete("delete from blog where id =#{blogId}")
    int deleteBlog(Blog blog);


    @Update("update blog set title=#{title},content=#{content},time=#{date}")
    int updateBlog(Blog blog);

}
