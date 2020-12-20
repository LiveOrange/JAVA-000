package io.kimmking.dubbo.demo.api;

public interface AccountService {
    void tccTransaction(Transaction transaction);

    void confirmTransaction(Transaction trans);

}
