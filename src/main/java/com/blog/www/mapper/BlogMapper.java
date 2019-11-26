package com.blog.www.mapper;
import com.blog.www.model.Blog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
    int insertBlog(Blog blog);



    /**
     * 修改博客内容
     * @param blog  博客
     * @return 修改数据条数
     */
    @Update("update blog set title=#{title},content=#{content},time=#{date} where id=#{id}")
    int updateBlog(Blog blog);

    /**
     * 从关联表中找到草稿箱中的博客的id
     * @param dId 草稿箱的id
     * @return 博客id
     */
    @Select("select b_id from db_collect where d_id = #{dId}")
    int selectBlogId(int dId);


    /**
     * 删除博客
     * @param blog
     * @return
     */
    @Delete("delete from blog where id =#{id}")
    int deleteBlog(Blog blog);

    /**
     * 用户通过收藏夹找到该收藏夹的所有博客
     * @param collectId 该收藏夹的id
     * @return 博客列表
     */
    @Select("select blog.* from blog,collect where collect.b_id=blog.id and collect.c_id = #{collectId}")
    @Results(id = "blog",value = {
            @Result(property = "id", column = "id"),
            @Result(property = "author", column = "u_id", one =@One(select = "com.blog.www.mapper.UserMapper.findUserById")),
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            @Result(property = "date", column = "time")
    })
    List<Blog> findBlogInCollect(int collectId);

    /**
     * 用户通过收藏夹找到该收藏夹的所有博客
     * @param collectId 该收藏夹的id
     * @return 博客列表
     */
    @Select("select blog.* from blog,db_collect where db_collect.b_id=blog.id and db_collect.d_id = #{collectId}")
    @ResultMap("blog")
    List<Blog> findBlogInAuto(int collectId);

    /**
     * 用户通过用户找到该用户写的所有博客
     * @param userId 该收藏夹的id
     * @return 博客列表
     */
    @Select("select * from blog where u_id = #{userId} order by id desc")
    @ResultMap("blog")
    List<Blog> findBlogByUser(int userId);


    /**
     * 通过博客id查询博客的内容
     * @param blogId 博客id
     * @return 查询结果
     */
    @ResultMap("blog")
    @Select("select * from blog where id = #{blogId}")
    Blog getBlogById(int blogId);




}
