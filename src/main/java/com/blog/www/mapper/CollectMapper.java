package com.blog.www.mapper;

import com.blog.www.model.Blog;
import com.blog.www.model.Collect;
import com.blog.www.model.UserCollect;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.*;

/**
 * @author lyx
 * @date 2019/11/16 16:12
 */
@Mapper
@Repository
public interface CollectMapper {

    /**
     * 删除所有用户收藏夹的某条博客
     * @param blog 博客
     * @return 删除条数
     */
    @Delete("delete from collect where b_id =#{id}")
    int deleteColBlog(Blog blog);

    /**
     *
     * @param id id
     * @return  增加的数字
     */
    @Insert("insert into d_collect (u_id,name,status) value (#{id},'默认收藏夹',0),(#{id},'草稿箱',1)")
    int insertDCollect(int id);

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

    /**
     * 将博客放至收藏夹中
     * @param collect 收藏夹关联表
     * @return 成功操作的数据条数
     */
    @Insert("insert into collect(c_id,b_id) values(#{userCollectId},#{id}) ")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "userCollectId", column = "c_id"),
            @Result(property = "blogId", column = "b_id")
    })
    @Options(useGeneratedKeys = true,keyProperty = "id" )
    int insertCollect(Collect collect);

    /**
     * 将博客放至默认收藏夹或草稿箱中
     * @param collect 收藏夹关联表
     * @return 增加条数
     */
    @Insert("insert into db_collect(d_id,b_id) values(#{userCollectId},#{blogId})")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "userCollectId", column = "d_id"),
            @Result(property = "blogId", column = "b_id")
    })
    @Options(useGeneratedKeys = true,keyProperty = "id" )
    int insertCollectToAuto(Collect collect);

    /**
     * 查询该用户的草稿箱的id
     * @param userId 用户id
     * @return 草稿箱的id
     */
    @Select("select id from d_collect where u_id = #{userId} and status = 1 ")
    int selectPaper(int userId);

    /**
     * 查询草稿箱中的数据条数
     * @param dId 用户的草稿箱id
     * @return 数据条数
     */
    @Select("select count(*) from db_collect where d_id = #{dId}")
    int selectPaperCount(int dId);

    /**
     * 查询该用户的默认收藏夹的id
     * @param userId 用户id
     * @return 用户的默认收藏夹的id
     */
    @Select("select id from d_collect where u_id = #{userId) and status = 0 ")
    int selectAuto(int userId);


}
