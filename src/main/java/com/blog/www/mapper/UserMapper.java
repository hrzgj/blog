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
    @Insert("insert into user (account,password,name,mail) values(#{account},#{password},#{name},#{mail})")
    void insertUser(User user);

    /**
     * 用户注册时获得的验证码
     * @param code 验证码
     * @param id    用户id
     */
    @Insert("insert into u_code (id, code) values(#{code},#{id})")
    void insertCode(String code,int id);

    /**
     * 登录
     * @param user 用户
     * @return  存在的用户
     */
    @Select("select * from user where account=#{account} and password=#{password} and status=1")
    User findByAccountAndPassword(User user);

    /**
     * 查找数据库是否存在对应账户或者邮箱的用户
     * @param user 用户
     * @return 存在的用户
     */
    @Select("select * from user where account=#{account} or mail=#{mail}")
    User findByAccountOrMail(User user);

    /**
     * 在u_code表查找验证码来注册用户
     * @param code 验证码
     * @return 数据条数
     */
    @Select("select id from u_code where code=#{code}")
    int findCode(@Param("code") String code);

    /**
     * 更新用户注册状态
     * @param id 用户主键
     */
    @Update("update user set status = 1 where id =#{id}")
    void updateStatus(@Param("id")int id);
}
