package com.blog.www.mapper;

import com.blog.www.model.Blog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author: chenyu
 * @date: 2019/11/16 13:18
 */
@Mapper
@Repository
public interface CollectMapper {

    @Delete("delete from collect where b_id =#{blogId}")
    int deleteColBlog(Blog blog);

    @Insert("insert into d_collect (u_id,name,status) value (#{id},默认收藏夹,0),(#{id},草稿箱,1)")
    int insertCollect(int id);
}
