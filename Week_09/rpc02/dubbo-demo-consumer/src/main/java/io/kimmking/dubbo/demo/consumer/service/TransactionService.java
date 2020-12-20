package io.kimmking.dubbo.demo.consumer.service;

import io.kimmking.dubbo.demo.api.Transaction;

public interface TransactionService {
    public boolean tccTransaction(Transaction trans);
}
