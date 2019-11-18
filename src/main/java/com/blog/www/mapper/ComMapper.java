package com.blog.www.mapper;

import com.blog.www.model.Blog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author: chenyu
 * @date: 2019/11/16 11:54
 */
@Mapper
@Repository
public interface ComMapper {


    @Delete("delete from comment where b_id=#{blogId}")
    int deleteBlogCom(Blog blog);
}
