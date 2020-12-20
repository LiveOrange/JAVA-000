//package io.kimmking.dubbo.demo.provider.service;
//
//import io.kimmking.dubbo.demo.api.User;
//import io.kimmking.dubbo.demo.api.UserService;
//import org.apache.dubbo.config.annotation.DubboService;
//
//import java.math.BigDecimal;
//
//@DubboService(version = "1.0.0")
//public class AccountServiceImpl implements AccountService {
//
//    @Override
//    public User findById(int id) {
//
///*
//        AccountInfo fromAccount = accountDO.queryAccount(trans.getFromAccountId());
//        if (fromAccount.getAmountEn().compareTo(trans.getAmount()) > -1) {
//            return false;
//        }
//        AccountInfo toAccount = accountDO.queryAccount(trans.getToAccountId());
//        BigDecimal amountCn = trans.getAmount().multiply(EXCHANGE_RATE);
//        if (toAccount.getAmountCn().compareTo(amountCn) > -1) {
//            return false;
//        }
//        fromAccount.setAmountEn(fromAccount.getAmountEn().subtract(trans.getAmount()));
//        int f = accountDO.updateAccount(fromAccount);
//        if (f != 1) {
//
//            throw new RuntimeException();
//        }
//        fromAccount.setAmountEn(fromAccount.getAmountEn().subtract(trans.getAmount()));
//        int t = accountDO.updateAccount(toAccount);
//        if (t != 1) {
//            throw new RuntimeException();
//        }*/
//
//        return new User(id, "KK" + System.currentTimeMillis());
//    }
//}
