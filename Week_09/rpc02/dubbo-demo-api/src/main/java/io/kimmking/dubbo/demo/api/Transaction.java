package io.kimmking.dubbo.demo.api;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Transaction implements java.io.Serializable{
    private BigDecimal amount;
    private String currencyType;
    private String fromAccountId;
    private String fromFreezeId;
    private String toAccountId;
    private String toFreezeId;
}

