package com.geek.sharding.dao;

import com.geek.sharding.entity.OrderDO;
import org.apache.ibatis.annotations.Insert;

public interface OrderDao {
    @Insert("insert into t_order(consumer_id,pay_amount,pay_time,status,create_time,update_time) values(#{consumerId},#{amount},now(),#{state},now(),now())")
    public int insertOrder(OrderDO orderDO);
}
