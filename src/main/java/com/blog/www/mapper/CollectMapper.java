package com.blog.www.mapper;

import com.blog.www.model.Blog;
import com.blog.www.model.Collect;
import com.blog.www.model.UserCollect;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author: chenyu
 * @date: 2019/11/16 13:18
 */
@Mapper
@Repository
public interface CollectMapper {

    /**
     * 删除所有用户收藏夹的某条博客
     * @param blog 博客
     * @return 删除条数
     */
    @Delete("delete from collect where b_id =#{blogId}")
    int deleteColBlog(Blog blog);

    /**
     *
     * @param id id
     * @return  增加的数字
     */
    @Insert("insert into d_collect (u_id,name,status) value (#{id},默认收藏夹,0),(#{id},草稿箱,1)")
    int insertCollect(int id);

    /**
     * 用户更新一篇博客的收藏夹
     * @param collect 博客收藏夹的中间表
     * @return 更新条数
     */
    @Update("update collect set c_id=#{userCollectId} where b_id=#{blogId} and id=#{id}")
    int changeBlogCollect(Collect collect);

    /**
     * 用户新增收藏夹
     * @param userCollect 收藏夹
     * @return 增加条数
     */
    @Insert("insert into u_collect (u_id,name,intro) value(#{user.id},#{name},#{intro})")
    int insertUserCollect(UserCollect userCollect);

}
