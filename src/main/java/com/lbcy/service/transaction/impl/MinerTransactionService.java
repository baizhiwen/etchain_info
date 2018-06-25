package com.lbcy.service.transaction.impl;

import com.lbcy.model.Transaction;
import com.lbcy.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * 记账交易 TxType=0
 * Created by 吴晓冬 on 2017/9/20.
 */
@Service
public class MinerTransactionService implements TransactionService
{
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void deal(Transaction transaction)
    {
        transaction.setTypeName("记账交易");
        //保存交易信息
        mongoTemplate.save(transaction);
    }
}
