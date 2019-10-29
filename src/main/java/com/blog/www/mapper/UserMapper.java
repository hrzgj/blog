package com.blog.www.mapper;

import com.blog.www.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    @Insert("insert into user(account,password) values(#{account},#{password})")
    int insert(User user);
}
