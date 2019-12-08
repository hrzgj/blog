package com.blog.www.mapper;

import com.blog.www.model.Blog;
import com.blog.www.model.Collect;
import com.blog.www.model.UserCollect;
import com.blog.www.model.User;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.log4j.Log4j;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author lyx
 * @date 2019/11/16 16:12
 */
@Mapper
@Repository
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
    @Insert("insert into d_collect (u_id,name,status) value (#{id},'默认收藏夹',0)")
    int insertDCollect(int id);

    /**
     * 用户更新一篇博客的收藏夹
     * @param collect 新收藏夹
     * @return 更新条数
     */
    @Update("update collect set c_id=#{userCollectId} where b_id=#{blogId} and id=#{id}")
    int changeBlogCollect(Collect collect);

    @Select("select count(1) from u_collect where u_id=#{userId} and id =#{collectOne} or id=#{collectTwo}")
    int findCollectByUserId(int userId,int collectOne,int collectTwo);
//    @Select("select count(1) from collect where c_id=#{userCollectId} and b_id=#{blogId}")
//    int findCollectExitBlog(Collect collect);

    /**
     * 查询用户对应非默认收藏夹中是否存在该博客
     * @param oldCollectId 旧收藏夹id
     * @param collect 收藏夹
     * @return 存在条数
     */
    @Select("select id from collect where c_id=#{oldCollectId} and b_id=#{collect.blogId} ")
    int findCollectExitBlogByUId(int oldCollectId,Collect collect);

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
     * 将博客放至收藏夹中
     * @param collect 收藏夹关联表
     * @return 成功操作的数据条数
     */
    @Insert("insert into collect(c_id,b_id) values(#{userCollectId},#{blogId}) ")
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
    @Select("select id from d_collect where u_id = #{userId} and status = 0")
    int selectAuto(int userId);

    /**
     * 查询是否此博客已存入默认收藏夹
     * @param blogId 博客id
     * @return 数据条数
     */
    @Select("select count(*) from db_collect where b_id = #{blogId} ")
    int selectBlogIsAuto(int blogId);

    /**
     * 删除草稿箱或者默认收藏夹中的博客
     * @param blogId 博客id
     * @param dId 草稿箱id
     * @return 数据条数
     */
    @Delete("delete from db_collect where b_id = #{blogId} and d_id = #{dId}")
    int deleteEditCollect(int blogId,int dId);

    /**
     * 登录用户查看自己收藏夹
     * @param user 用户
     * @return 收藏夹的list
     */
    @Select("select id,u_id,name,status as intro from  d_collect where u_id=#{id} and status = 0 union all select id,u_id,name,intro from u_collect where u_id=#{id}")
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

    /**
     * 查看默认收藏夹是否有该博客
     * @param DId 默认收藏夹id
     * @param blogId 博客id
     * @return 是否查找成功
     */
    @Select("select count(1) from db_collect where d_id=#{DId} and b_id =#{blogId}")
    int findNorMalCollectExitBlog(int DId,int blogId);

    /**
     * 删除非默认收藏夹的一篇博客
     * @param collect 非默认收藏夹
     * @return  删除条数
     */
    @Delete("delete from collect where c_id=#{userCollectId} and b_id=#{blogId}")
    int deleteCollectBlog(Collect collect);

    /**
     * 删除默认收藏夹的一篇博客
     * @param collect 非默认收藏夹
     * @return  删除条数
     */
    @Delete("delete from db_collect where d_id=#{userCollectId} and b_id=#{blogId}")
    int deleteNormalCollectBlog(Collect collect);

    /**
     * 更新非默认收藏夹名称和简介
     * @param userCollect 收藏夹
     * @return 是否更新成功
     */
    @Update("update u_collect set name=#{name},intro=#{intro} where id=#{id}")
    int updateCollectNameAndIntro(UserCollect userCollect);

    /**
     * 更新默认收藏夹名称
     * @param userCollect 收藏夹
     * @return 更新条数
     */
    @Update("update d_collect set name=#{name} where id=#{id} and status=0 ")
    int updateNormalCollectName(UserCollect userCollect);

}
