package com.lbcy.service.transaction.impl;

import com.lbcy.model.Asset;
import com.lbcy.model.Transaction;
import com.lbcy.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * 资产注册交易 TxType=64
 * Created by 吴晓冬 on 2017/9/20.
 */
@Service
public class RegisterTransactionService implements TransactionService
{
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void deal(Transaction transaction)
    {
        Transaction.Payload payload = transaction.getPayload();

        //资产信息输出化
        Asset asset = payload.getAsset();
        asset.setAssetID(transaction.getHash());
        asset.setAmount(payload.getAmount());

        //保存资产信息
        mongoTemplate.save(asset);

        //保存交易信息
        transaction.setTypeName("资产注册交易");
        mongoTemplate.save(transaction);
    }
}
