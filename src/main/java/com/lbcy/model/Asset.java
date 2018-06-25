package com.lbcy.model;

import com.google.gson.annotations.SerializedName;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * Created by 吴晓冬 on 2017/9/20.
 */
@Document(collection="dna_asset")
public class Asset
{
    /**
     * 资产编号
     */
    @Id
    @SerializedName("AssetID")
    private String assetID;

    /**
     * 资产名称
     */
    @SerializedName("Name")
    private String name;

    /**
     * 资产描述
     */
    @SerializedName("Description")
    private String description;

    /**
     * 资产精度
     */
    @SerializedName("Precision")
    private Integer precision;

    /**
     * 资产类型
     * 0 currency，1 share，8 invoice，9 token
     */
    @SerializedName("AssetType")
    private Integer assetType;

    /**
     * 记账模型
     * 0 UTXO模型，1 记账模型
     * 目前只支持0
     */
    @SerializedName("RecordType")
    private Integer recordType;

    /**
     * 资产总数
     */
    @SerializedName("Amount")
    private BigDecimal amount;

    public String getAssetID()
    {
        return assetID;
    }

    public void setAssetID(String assetID)
    {
        this.assetID = assetID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getPrecision()
    {
        return precision;
    }

    public void setPrecision(Integer precision)
    {
        this.precision = precision;
    }

    public Integer getAssetType()
    {
        return assetType;
    }

    public void setAssetType(Integer assetType)
    {
        this.assetType = assetType;
    }

    public Integer getRecordType()
    {
        return recordType;
    }

    public void setRecordType(Integer recordType)
    {
        this.recordType = recordType;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }
}
