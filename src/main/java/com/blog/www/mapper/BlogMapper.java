package com.blog.www.mapper;
import com.blog.www.model.Blog;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.w3c.dom.Text;

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
     * 新增草稿箱博客
     * @param blog 博客
     * @return 成功处理的数据数
     */
    @Insert("insert into e_blog(u_id,content,time,title) values(#{author.id},#{content},#{date},#{title})")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "author", column = "u_id", one =@One(select = "com.blog.www.mapper.UserMapper.findUserById")),
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            @Result(property = "date", column = "time")
    })
    @Options(useGeneratedKeys = true,keyProperty = "id" )
    int insertEditBlog(Blog blog);

    /**
     * 删除草稿箱中的博客
     * @param blogId 博客id
     * @return 数据数
     */
    @Delete("delete from e_blog where id = #{blogId}")
    int deleteEditBlog(int blogId);

    /**
     * 修改博客内容
     * @param blog  博客
     * @return 修改数据条数
     */
    @Update("update blog set title=#{title},content=#{content},time=#{date} where id=#{id}")
    int updateBlog(Blog blog);

    /**
     * 修改草稿箱博客内容
     * @param blog  博客
     * @return 修改数据条数
     */
    @Update("update e_blog set title=#{title},content=#{content},time=#{date} where id=#{id}")
    int updateEditBlog(Blog blog);

    /**
     * 从草稿箱中找到博客的数量
     * @param id 博客id
     * @return 博客id
     */
    @Select("select count(*) from e_blog where id = #{id}")
    int selectBlogCount(int id);


    /**
     * 删除博客
     * @param blog 博客id
     * @return 删除条数
     */
    @Delete("delete from blog where id =#{id}")
    int deleteBlog(Blog blog);

    /**
     * 首页博客查询，分页查询
     * @return 每页的博客
     */
    @Select("select * from blog order by time desc")
    @ResultMap("blog")
    Page<Blog> findPageBlog();


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
     * 用户通过用户id找到草稿箱的所有博客
     * @param userId 该用户的id
     * @return 博客列表
     */
    @Select("select * from e_blog where u_id = #{userId} order by id desc")
    @ResultMap("blog")
    List<Blog> findBlogInEdit(int userId);

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

    /**
     * 通过博客id查询博客是否存在
     *@author chenyu
     *@date 20:29 2019/12/9
     *@param blogId 博客id
     *@return  博客是否存在
     **/
    @Select("select count(1) from blog where id = #{blogId}")
    int findBlogById(int blogId);

    /**
     * 通过草稿id查询草稿的内容
     * @param blogId 草稿id
     * @return 查询结果
     */
    @ResultMap("blog")
    @Select("select * from e_blog where id = #{blogId}")
    Blog getEditBlogById(int blogId);

    /**
     * 搜索博客，分页展示
     * @param seek 搜索内容
     * @return 结果集
     */
    @Select("select * from blog where content like concat('%',#{seek},'%') or title like concat('%',#{seek},'%') order by time ")
    @ResultMap("blog")
    Page<Blog> seekBlog(String seek);

}
