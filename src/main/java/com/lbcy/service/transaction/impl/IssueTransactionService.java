package com.lbcy.service.transaction.impl;

import com.lbcy.model.Asset;
import com.lbcy.model.Transaction;
import com.lbcy.model.TransactionOutput;
import com.lbcy.service.AddressService;
import com.lbcy.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 资产发行交易 TxType=1
 * Created by 吴晓冬 on 2017/9/21.
 */
@Service
public class IssueTransactionService implements TransactionService
{
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AddressService addressService;

    @Override
    public void deal(Transaction transaction)
    {
        List<TransactionOutput> outputs = transaction.getOutputs();
        if (!CollectionUtils.isEmpty(outputs))
        {
            for (int i = 0; i < outputs.size(); i++)
            {
                TransactionOutput output = outputs.get(i);
                //id为交易号_数组中位置
                output.setId(transaction.getHash() + "_" + i);

                //根据资产编号找到资产对应的信息
                Asset asset = mongoTemplate.findById(output.getAssetID(), Asset.class);
                output.setAsset(asset);

                //保存UTXO模型output信息
                mongoTemplate.save(output);
            }
        }

        //保存地址下交易
        addressService.save(transaction);
        //保存交易信息
        transaction.setTypeName("资产发行交易");
        mongoTemplate.save(transaction);
    }
}