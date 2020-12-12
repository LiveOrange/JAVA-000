/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.geek.transaction;

import com.geek.sharding.dao.AccountDao;
import com.geek.sharding.dao.OrderDao;
import com.geek.sharding.entity.AccountDO;
import com.geek.sharding.entity.OrderDO;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class XAOrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private AccountDao accountDao;

    @Transactional(rollbackFor = Exception.class)
    @ShardingTransactionType(TransactionType.XA)
    public void saveOrder(OrderDO orderDO) {
        insertOrder(orderDO);
        cutAccount(orderDO);
    }

    private void insertOrder(OrderDO orderDO) {
        orderDO.setState("T");
        int i = orderDao.insertOrder(orderDO);
        if (i != 1) {
            throw new RuntimeException("insert Order error");
        }
    }

    private void cutAccount(OrderDO orderDO) {
        AccountDO accountDO = accountDao.queryAccountById(orderDO.getConsumerId());
        if (0 > accountDO.getAccount().compareTo(orderDO.getAmount())) {
            throw new RuntimeException("account not enough");
        }

        accountDO.setAccount(accountDO.getAccount().subtract(orderDO.getAmount()));
        int a = accountDao.cutAccount(accountDO);
        if (a != 1) {
            throw new RuntimeException("cutAccount error");
        }
    }

}
