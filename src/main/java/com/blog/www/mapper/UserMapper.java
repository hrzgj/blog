package com.blog.www.mapper;

import com.blog.www.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author chenyu
 */
@Mapper
@Repository
public interface UserMapper {

    @Insert("insert into user(account,password) values(#{account},#{password})")
    String insert(User user);

    @Select("select * from user where account=#{account} and password=#{password}")
    User findByAccountAndPassword(User user);

    @Select("select * from user where account=#{account} or mail=#{mail}")
    User findByAccountOrMail(User user);
}
