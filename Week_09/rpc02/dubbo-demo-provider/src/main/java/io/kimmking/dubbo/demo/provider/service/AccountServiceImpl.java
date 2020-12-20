package io.kimmking.dubbo.demo.provider.service;

import io.kimmking.dubbo.demo.api.AccountService;
import io.kimmking.dubbo.demo.api.Transaction;
import io.kimmking.dubbo.demo.provider.dao.Account;
import io.kimmking.dubbo.demo.provider.entity.AccountInfo;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@DubboService
public class AccountServiceImpl implements AccountService {
    private static final BigDecimal EXCHANGE_RATE = new BigDecimal("6.5348");

    @Autowired
    private Account accountDO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @HmilyTCC(confirmMethod = "confirmTransaction")
    public void tccTransaction(Transaction trans) {
        AccountInfo fromAccount = accountDO.queryAccount(trans.getFromAccountId());
        if (fromAccount.getAmountEn().compareTo(trans.getAmount()) < 0) {
            throw new RuntimeException();
        }
        AccountInfo toAccount = accountDO.queryAccount(trans.getToAccountId());
        BigDecimal amountCn = trans.getAmount().multiply(EXCHANGE_RATE);
        if (toAccount.getAmountCn().compareTo(amountCn) < 0) {
            throw new RuntimeException();
        }
        fromAccount.setAmountEn(fromAccount.getAmountEn().subtract(trans.getAmount()));
        int f = accountDO.updateAccount(fromAccount);
        if (f != 1) {
            throw new RuntimeException();
        }
        toAccount.setAmountCn(toAccount.getAmountCn().subtract(amountCn));
        int t = accountDO.updateAccount(toAccount);
        if (t != 1) {
            throw new RuntimeException();
        }
    }

    @Override
    public void confirmTransaction(Transaction trans) {
        AccountInfo fromAccount = accountDO.queryAccount(trans.getFromAccountId());
        BigDecimal fromAmountCn = fromAccount.getAmountCn();
        fromAccount.setAmountCn(fromAmountCn.add(trans.getAmount().multiply(EXCHANGE_RATE)));
        accountDO.updateAccount(fromAccount);

        AccountInfo toAccount = accountDO.queryAccount(trans.getToAccountId());
        BigDecimal toAmountEn = toAccount.getAmountEn();
        toAccount.setAmountEn(toAmountEn.add(trans.getAmount()));
        accountDO.updateAccount(toAccount);
    }
}
