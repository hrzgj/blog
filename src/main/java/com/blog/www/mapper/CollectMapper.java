package com.blog.www.mapper;

import com.blog.www.model.Blog;
import com.blog.www.model.Collect;
import com.blog.www.model.User;
import com.blog.www.model.UserCollect;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: chenyu
 * @date: 2019/11/16 13:18
 */
@Mapper
@Repository
//@CacheNamespace(blocking = true)
public interface CollectMapper {

    /**
     * 删除所有用户非默认收藏夹的某条博客
     * @param blog 博客
     * @return 删除条数
     */
    @Delete("delete from collect where b_id =#{id}")
    int deleteColAllBlogByBlogId(Blog blog);

    /**
     * 删除所有用户默认收藏夹的某条博客
     * @param blog 博客
     * @return  删除成功
     */
    @Delete("delete from db_collect where b_id =#{id}")
    int deleteNormalColAllBlog(Blog blog);

    /**
     *用户注册默认增加默认收藏夹和草稿箱
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

//    @Select("select count(1) from collect where c_id=#{userCollectId} and b_id=#{blogId}")
//    int findCollectExitBlog(Collect collect);

    /**
     * 查询用户对应非默认收藏夹中是否存在该博客
     * @param userId 用户id
     * @param collect 收藏夹
     * @return 存在条数
     */
    //and collect.id=#{collect.id}
    @Select("select count(1) from collect,u_collect where u_id=#{userId} " +
            "and b_id=#{collect.blogId} and c_id=u_collect.id and collect.id=#{collect.id}")
    int findCollectExitBlogByUId(int userId,Collect collect);

    @Select("select count(1) from collect where c_id=#{userCollectId} and b_id=#{blogId}")
    int findCollectExitBlog(Collect collect);

    @Select("select count(1) from u_collect where u_id=#{userId} and id=#{collect.userCollectId}")
    int checkUserCollect(Collect collect,int userId);

    /**
     * 用户新增收藏夹
     * @param userCollect 收藏夹
     * @return 增加条数
     */
    @Insert("insert into u_collect (u_id,name,intro) value(#{userId},#{name},#{intro})")
    int insertUserCollect(UserCollect userCollect);

    /**
     * 登录用户查看自己收藏夹
     * @param user 用户
     * @return 收藏夹的list
     */
    //,one = @One(select = "com.blog.www.mapper.UserMapper.findUserById")
    @Select("select * from u_collect where u_id=#{id}")
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "u_id", property = "userId"),
            @Result(column = "name", property = "name"),
            @Result(column = "intro", property = "intro")
    })
    List<UserCollect> findUserCollect(User user);

    /**
     * 删除用户某个收藏夹
     * @param userCollect 用户收藏夹
     * @return  删除条数
     */
    @Delete("delete from u_collect where id=#{id} and u_id=#{userId} ")
    int deleteUserCollect(UserCollect userCollect);

    /**
     * 删除用户某个收藏夹下得所有博客
     * @param userCollect 用户收藏夹
     */
    @Delete("delete from collect where c_id =#{id}")
    void deleteBlogCollect(UserCollect userCollect);

    /**
     * 用户将某一个博客存入非默认收藏夹
     * @param collect 博客和收藏夹中间表
     * @return  是否增加成功
     */
    @Insert("insert into collect (c_id,b_id) value (#{userCollectId},#{blogId})")
    int insertCollectBlog(Collect collect);

    /**
     * 删除用户某个收藏夹中的某个博客
     * @param collect 博客和非默认收藏夹中间表
     * @return 删除成功
     */
    @Delete("delete from collect where b_id=#{blogId} and c_id=#{userCollectId}")
    int deleteBlogByCIdAndBid(Collect collect);

    /**
     * 用户的默认收藏夹增加一条博客
     * @param DId 用户默认收藏夹的id
     * @param blogId    博客id
     * @return  插入成功
     */
    @Insert("insert into db_collect (b_id,d_id) value (#{blogId},#{DId}) ")
    int insertNormalCollect(int DId,int blogId);

    /**
     * 用户查找默认收藏夹的id
     * @param userId 用户id
     * @return  是否查找成功
     */
    @Select("select id  from d_collect where u_Id=#{userId} and status =0")
    int findNormalCollectByUId(int userId);

    @Select("select count(1) from db_collect where d_id=#{DId} and b_id =#{blogId}")
    int findNorMalCollectExitBlog(int DId,int blogId);
}
