package com.geek.sharding.dao;

import com.geek.sharding.entity.UserDO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface UserDao {


    @Select("select * from t_user")
    @Results(id = "stockMap", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_name", property = "userName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "consumer_id", property = "consumerId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
    })
    public List<UserDO> queryUserAll();

    @Insert("insert into t_user(user_name,gender,state,create_time,update_time) values(#{userName},#{gender},'T',now(),now())")
    public int insertUser(UserDO userDO);

    @Update("update t_user set user_name = #{userName} where consumer_id = #{consumerId}")
    public int updateUser(@Param("userName") String name, @Param("consumerId") Long consumerId);


}
