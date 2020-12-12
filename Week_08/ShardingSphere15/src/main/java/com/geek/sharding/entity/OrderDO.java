package com.geek.sharding.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderDO implements Serializable {
    private Long consumerId;
    private String orderId;
    private String state;
    private BigDecimal amount;
    private Date createTime;
    private Date updateTime;
}
