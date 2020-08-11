package com.blog.www.mapper;


import com.blog.www.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author chenyu
 */
@Mapper
@Repository
public interface UserMapper {

    /**
     * 插入用户数据
     * @param user 登录用户
     */
    @Insert("insert into user (account,password,name,mail,status) values(#{account},#{password},#{name},#{mail},1)")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    Integer insertUser(User user);

    /**
     * 用户注册时获得的验证码
     * @param code 验证码
     * @param id    用户id
     */
    @Insert("insert into u_code (id, code) values(#{id},#{code})")
    void insertCode(int id,String code);

    /**
     * 登录
     * @param user 用户
     * @return  存在的用户
     */
    @Results({
            @Result(property="id",column="id"),
            @Result(property="account",column="account"),
            @Result(property="password",column="password"),
            @Result(property="photo",column="photo"),
            @Result(property="name",column="name"),
            @Result(property="mail",column="mail"),
            @Result(property="status",column="status")
    })
    @Select("select * from user where account=#{account} and password=#{password}")
    User findByAccountAndPassword(User user);

    /**
     *通过id查询用户
     * @param id
     * @return
     */
    @Select("select id,account,name,photo from user where id = #{id}")
    User findUserById(int id);

    @Select("select count(1) from user where id=#{id}")
    int findUserExitById(int id);

//    /**
//     *通过id查询用户
//     * @param user user
//     * @return
//     */
//    @Select("select * from user where id = #{id}")
//    User findUserById(User user);

    /**
     * 通过邮箱找到用户信息
     * @param mail 邮箱
     * @return
     */
    @Select("select * from user where mail = #{mail}")
    User findUserByMail(String mail);

    /**
     * 查看用户账号是否存在
     * @param user 用户
     * @return  是否存在
     */
    @Select("select count(*) from user where account=#{account}")
    int findByAccount(User user);

    /**
     * 查看用户邮箱是否存在
     * @param user 用户邮箱
     * @return  是否存在
     */
    @Select("select count(*) from user where mail =#{mail}")
    int findByMail(User user);

    /**
     * 查找数据库是否存在对应账户或者邮箱的用户
     * @param user 用户
     * @return 是否存在
     */
    @Select("select count(*) from user where account=#{account} or mail=#{mail}")
    int findByAccountOrMail(User user);

    /**
     * 在u_code表查找验证码来注册用户
     * @param code 验证码
     * @return 数据条数
     */
    @Select("select id from u_code where code=#{code}")
    Integer findCode(@Param("code") String code);

    /**
     * 更新用户注册状态
     * @param id 用户主键
     */
    @Update("update user set status = 1 where id =#{id}")
    void updateStatus(@Param("id")int id);

    /**
     * @param id 用户id
     */
    @Delete("delete from u_code where id=#{id}")
    void deleteCode(@Param("id") int id);

    /**
     * 用户图片更新
     * @param photo 图片名称
     * @param id   用户id
     */
    @Update("update user set photo =#{photo} where id =#{id}")
    void updatePhoto(String photo,int id);


    /**
     * 修改密码的操作
     * @param id 用户id
     * @param newPsw 新密码
     * @return 数据条数
     */
    @Update("update user set password =#{password} where id = #{id}")
    int updatePassword(@Param("id") int id,@Param("password") String newPsw);



}
