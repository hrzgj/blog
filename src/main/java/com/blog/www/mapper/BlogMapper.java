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


    @Insert("insert into blog(id,u_id,content,title,time) values(#{id},#{author.id},#{content},#{title},#{date})")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "author", column = "u_id", one =@One(select = "com.blog.www.mapper.UserMapper.findUserById")),
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            @Result(property = "date", column = "time")
    })
    int insertPassage(Blog blog);


    /**
     * 用户删除自己的博客
     * @param blog 博客
     * @return 删除条数
     */
    @Delete("delete from blog where id =#{id}")
    int deleteBlog(Blog blog);


    /**
     * @param blog  博客
     * @return
     */
    @Update("update blog set title=#{title},content=#{content},time=#{date} where id=#{id}")
    int updateBlog(Blog blog);

}
