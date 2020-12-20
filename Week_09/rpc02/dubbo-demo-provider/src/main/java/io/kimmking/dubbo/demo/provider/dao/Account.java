package io.kimmking.dubbo.demo.provider.dao;

import io.kimmking.dubbo.demo.provider.entity.AccountInfo;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface Account {
    @Select("select * from t_account where account_id=#{accountId}")
    @Results(id = "accountMap", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "account_id", property = "accountId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "amount_cn", property = "amountCn", jdbcType = JdbcType.DECIMAL),
            @Result(column = "amount_en", property = "amountEn", jdbcType = JdbcType.DECIMAL),
            @Result(column = "version", property = "version", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    AccountInfo queryAccount(String accountId);

    @Update("update t_account set amount_cn = #{amountCn}, amount_en = #{amountEn}, version = #{version}+1 where account_id = #{accountId} and version = #{version}")
    int updateAccount(AccountInfo accountId);
}
