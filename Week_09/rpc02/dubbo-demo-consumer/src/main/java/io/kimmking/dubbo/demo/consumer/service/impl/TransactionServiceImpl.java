package io.kimmking.dubbo.demo.consumer.service.impl;

import io.kimmking.dubbo.demo.api.AccountService;
import io.kimmking.dubbo.demo.api.Transaction;
import io.kimmking.dubbo.demo.consumer.dao.Freeze;
import io.kimmking.dubbo.demo.consumer.entity.FreezeInfo;
import io.kimmking.dubbo.demo.consumer.service.TransactionService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransactionServiceImpl implements TransactionService {
    @DubboReference(check = false)
    private AccountService accountService;

    @Autowired
    private Freeze freezeDO;

    @Override
    @HmilyTCC(confirmMethod = "confirmTransaction", cancelMethod = "cancelTransaction")
    @Transactional(rollbackFor = Exception.class)
    public boolean tccTransaction(Transaction trans) {
        FreezeInfo toFreeze = buildFreeze(trans.getFromAccountId(), trans.getAmount(), trans.getFromFreezeId());
        FreezeInfo fromFreeze = buildFreeze(trans.getFromAccountId(), trans.getAmount(), trans.getToFreezeId());
        freezeDO.insertAccount(toFreeze);
        freezeDO.insertAccount(fromFreeze);
        accountService.tccTransaction(trans);
        return true;
    }

    public void confirmTransaction(Transaction trans) {
        freezeDO.updateAccount(trans.getFromFreezeId(), "SUCC");
        freezeDO.updateAccount(trans.getToFreezeId(), "SUCC");
        accountService.confirmTransaction(trans);
    }

    public void cancelTransaction(Transaction trans) {
        freezeDO.updateAccount(trans.getFromFreezeId(), "FAIL");
        freezeDO.updateAccount(trans.getToFreezeId(), "FAIL");
    }

    public FreezeInfo buildFreeze(String accountId, BigDecimal amount, String freezeId) {
        FreezeInfo freeze = new FreezeInfo();
        freeze.setState("ING");
        freeze.setAmount(amount);
        freeze.setVersion(1L);
        freeze.setFreezeId(freezeId);
        freeze.setCurrencyType("CNY");
        freeze.setAccountId(accountId);
        return freeze;
    }
}
