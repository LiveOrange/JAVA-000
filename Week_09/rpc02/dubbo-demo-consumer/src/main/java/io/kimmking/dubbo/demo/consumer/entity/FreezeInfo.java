package io.kimmking.dubbo.demo.consumer.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FreezeInfo {
    private Long id;
    private String state;
    private String freezeId;
    private String accountId;
    private BigDecimal amount;
    private String currencyType;
    private Long version;
    private Date createTime;
    private Date updateTime;
}
