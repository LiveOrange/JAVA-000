package com.geek.sharding.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDO {
    private Long id;
    private Long consumerId;
    private BigDecimal account;
    private Long version;
}
