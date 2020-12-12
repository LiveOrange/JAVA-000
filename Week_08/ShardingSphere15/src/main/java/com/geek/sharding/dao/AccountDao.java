package com.geek.sharding.dao;

import com.geek.sharding.entity.AccountDO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface AccountDao {
    @Update("update t_account set account = #{account}, version = #{version}+1 where consumer_id = #{consumerId} and version = #{version}")
    int cutAccount(AccountDO accountDO);

    @Select("select * from t_account where consumer_id = #{consumerId}")
    @Results(id = "stockMap", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "consumer_id", property = "consumerId", jdbcType = JdbcType.BIGINT),
            @Result(column = "account", property = "account", jdbcType = JdbcType.DECIMAL),
            @Result(column = "version", property = "version", jdbcType = JdbcType.BIGINT),
    })
    AccountDO queryAccountById(Long consumerId);
}
