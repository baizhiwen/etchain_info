package com.lbcy.model;

import com.google.gson.annotations.SerializedName;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by 吴晓冬 on 2017/9/21.
 */
@Document(collection="dna_address")
public class Address
{
    public Address()
    {

    }

    public Address(String id)
    {
        this.id = id;
    }
    /**
     * 地址
     */
    @Id
    private String id;

    /**
     * 地址下相关所有交易
     */
    @DBRef(lazy=true)
    private List<Transaction> transactions = new ArrayList<>();

    /**
     * 最后交易时间
     */
    private Long lastTime;

    /**
     * 地址生成时间
     */
    private Long createTime;

    /**
     * 各资产余额
     */
    private List<Asset> assets;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public List<Transaction> getTransactions()
    {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions)
    {
        this.transactions = transactions;
    }

    public Long getLastTime()
    {
        return lastTime;
    }

    public void setLastTime(Long lastTime)
    {
        this.lastTime = lastTime;
    }

    public Long getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Long createTime)
    {
        this.createTime = createTime;
    }

    public List<Asset> getAssets()
    {
        return assets;
    }

    public void setAssets(List<Asset> assets)
    {
        this.assets = assets;
    }

    public void setAssets(Map<String, BigDecimal> assetMap)
    {
        List<Address.Asset> assets = new ArrayList<>();
        Iterator<Map.Entry<String, BigDecimal>> assetIterator = assetMap.entrySet().iterator();
        while (assetIterator.hasNext())
        {
            Map.Entry<String, BigDecimal> entry = assetIterator.next();
            Asset asset = new Asset();
            asset.setAssetName(entry.getKey());
            asset.setAssetValue(entry.getValue());

            assets.add(asset);
        }

        this.assets = assets;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        Address address = (Address) o;

        return id.equals(address.id);
    }

    public class Asset
    {
        private String assetName;

        private BigDecimal assetValue;

        public String getAssetName()
        {
            return assetName;
        }

        public void setAssetName(String assetName)
        {
            this.assetName = assetName;
        }

        public BigDecimal getAssetValue()
        {
            return assetValue;
        }

        public void setAssetValue(BigDecimal assetValue)
        {
            this.assetValue = assetValue;
        }
    }
}
