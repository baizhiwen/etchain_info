package com.lbcy.service;

import com.lbcy.model.Address;
import com.lbcy.model.Transaction;
import com.lbcy.model.TransactionOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by 吴晓冬 on 2017/9/21.
 */
@Service
public class AddressService
{
    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(Transaction transaction)
    {
        //找到交易相关的所有地址
        List<Address> addresses = new ArrayList<>();
        List<Transaction.UTXOInput> utxoInputs = transaction.getUtxoInputs();
        if (!CollectionUtils.isEmpty(utxoInputs))
        {
            for (int i = 0; i < utxoInputs.size(); i++)
            {
                Transaction.UTXOInput utxoInput = utxoInputs.get(i);
                TransactionOutput output = utxoInput.getOutput();

                String addressId = output.getAddress();

                Address address = mongoTemplate.findById(addressId, Address.class);
                if (null == address)
                {
                    address = new Address(addressId);
                }
                if (!addresses.contains(address))
                {
                    addresses.add(address);
                }
            }
        }
        List<TransactionOutput> outputs = transaction.getOutputs();
        if (!CollectionUtils.isEmpty(outputs))
        {
            for (int i = 0; i < outputs.size(); i++)
            {
                TransactionOutput output = outputs.get(i);

                String addressId = output.getAddress();

                Address address = mongoTemplate.findById(addressId, Address.class);
                if (null == address)
                {
                    address = new Address(addressId);
                    address.setCreateTime(transaction.getTimestamp());
                }
                if (!addresses.contains(address))
                {
                    addresses.add(address);
                }
            }
        }


        for (Address address : addresses)
        {
            //将该交易添加到该地址的交易流水里
            if (!address.getTransactions().contains(transaction))
            {
                address.getTransactions().add(0, transaction);
            }

            //重新计算地址下各资产余额
            Map<String, BigDecimal> assetMap = new LinkedHashMap<>();
            for (Transaction tx : address.getTransactions())
            {
                for (TransactionOutput output : tx.getOutputs())
                {
                    if (address.getId().equals(output.getAddress()))
                    {
                        String assetName = output.getAsset().getName();

                        BigDecimal assetValue = output.getValue();

                        BigDecimal beforeValue = assetMap.get(assetName);

                        if (null == beforeValue)
                        {
                            beforeValue = new BigDecimal(0);
                        }

                        assetValue = beforeValue.add(assetValue);
                        assetMap.put(assetName, assetValue);
                    }
                }

                for (Transaction.UTXOInput utxoInput : tx.getUtxoInputs())
                {
                    TransactionOutput output = utxoInput.getOutput();
                    if (address.getId().equals(output.getAddress()))
                    {
                        String assetName = output.getAsset().getName();
                        BigDecimal assetValue = output.getValue();

                        BigDecimal beforeValue = assetMap.get(assetName);

                        if (null == beforeValue)
                        {
                            beforeValue = new BigDecimal(0);
                        }

                        assetValue = beforeValue.subtract(assetValue);
                        assetMap.put(assetName, assetValue);
                    }
                }
            }

            address.setLastTime(transaction.getTimestamp());
            address.setAssets(assetMap);
            mongoTemplate.save(address);
        }
    }
}
