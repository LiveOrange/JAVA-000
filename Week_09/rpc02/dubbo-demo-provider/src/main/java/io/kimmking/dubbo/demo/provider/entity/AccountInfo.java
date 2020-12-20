package io.kimmking.dubbo.demo.provider.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AccountInfo {
    private Long id;
    private String accountId;
    private BigDecimal amountCn;
    private BigDecimal amountEn;
    private Long version;
    private Date createTime;
    private Date updateTime;
}
