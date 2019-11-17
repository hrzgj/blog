package com.blog.www.mapper;

import com.blog.www.model.Collect;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author lyx
 * @date 2019/11/16 16:12
 */
@Mapper
@Repository
public interface CollectMapper {

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
     * 将博客放至默认收藏夹中
     * @param collect 收藏夹关联表
     * @return
     */
    @Insert("insert into collect(c_id,b_id) values(#{userCollectId},#{blogId})")
    int insertCollectToAuto(Collect collect);


}
