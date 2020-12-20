package io.kimmking.dubbo.demo.consumer.controller;

import io.kimmking.dubbo.demo.api.Transaction;
import io.kimmking.dubbo.demo.consumer.service.TransactionService;
import io.kimmking.dubbo.demo.consumer.service.impl.TransactionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/trans")
public class ForeignExchange {
    @Autowired
    private TransactionService accountService;

    @PostMapping(value = "/start")
    public boolean transaction(Transaction transaction) {
        System.out.println(transaction.toString());
        transaction.setFromFreezeId(UUID.randomUUID().toString());
        transaction.setToFreezeId(UUID.randomUUID().toString());
        return accountService.tccTransaction(transaction);
    }
}
