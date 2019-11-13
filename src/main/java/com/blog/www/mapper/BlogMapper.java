package com.blog.www.mapper;
import com.blog.www.model.Blog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author: chenyu
 * @date: 2019/10/29 21:32
 */
@Mapper
public interface BlogMapper {


    @Insert("insert into blog(id,userId,content,time,title) values(#{blogId},#{author.id},#{content},#{title},#{date})")
    boolean insertPassage(Blog blog);

}
