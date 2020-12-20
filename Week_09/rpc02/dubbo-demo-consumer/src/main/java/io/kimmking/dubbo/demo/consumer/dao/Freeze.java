package io.kimmking.dubbo.demo.consumer.dao;


import io.kimmking.dubbo.demo.consumer.entity.FreezeInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface Freeze {
    @Insert("insert into t_freeze(freeze_id,account_id, amount, currency_type,state,create_time,update_time) values(#{freezeId}, #{accountId}, #{amount},#{currencyType},#{state},now(),now())" )
    @Results(id = "freezeMap", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "freeze_id", property = "freezeId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "account_id", property = "accountId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "amount", property = "amount", jdbcType = JdbcType.DECIMAL),
            @Result(column = "state", property = "state", jdbcType = JdbcType.VARCHAR),
            @Result(column = "currency_type", property = "currencyType", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    int insertAccount(FreezeInfo freezeInfo);

    @Update("update t_freeze set state = #{status} where freeze_id = #{freezeId}")
    int updateAccount(String freezeId, String status);
}
