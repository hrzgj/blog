package com.blog.www.mapper;
import com.blog.www.model.Blog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: chenyu
 * @date: 2019/10/29 21:32
 */
@Mapper
@Repository
public interface BlogMapper {

    /**
     * 新增博客
     * @param blog 博客
     * @return 成功处理的数据数
     */
    @Insert("insert into blog(u_id,content,time,title) values(#{author.id},#{content},#{date},#{title})")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "author", column = "u_id", one =@One(select = "com.blog.www.mapper.UserMapper.findUserById")),
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            @Result(property = "date", column = "time")
    })
    @Options(useGeneratedKeys = true,keyProperty = "id" )
    int insertPassage(Blog blog);







    @Delete("delete from blog where id =#{id}")
    int deleteBlog(Blog blog);



}
