package com.geek.sharding.dao;

import com.geek.sharding.entity.UserDO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface UserDao {
    @Select("select * from t_user")
    @Results(id = "stockMap", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_name", property = "name", jdbcType = JdbcType.VARCHAR),
    })
    public List<UserDO> queryUserAll();

    @Insert("insert into t_user(user_name) values(#{name})")
    public int insertUser(String name);

    @Update("update t_user set user_name = #{userName} where id = #{id}")
    public int updateUser(@Param("userName") String name,@Param("id") Long id);
}
