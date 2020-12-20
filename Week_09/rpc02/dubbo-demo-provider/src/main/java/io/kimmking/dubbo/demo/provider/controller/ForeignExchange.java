package io.kimmking.dubbo.demo.provider.controller;

import io.kimmking.dubbo.demo.api.Transaction;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trans")
public class ForeignExchange {
    @PostMapping(value = "/start")
    public boolean transaction(Transaction transaction) {
        return true;
    }
}
