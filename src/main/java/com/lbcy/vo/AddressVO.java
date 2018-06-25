package com.lbcy.vo;

import com.lbcy.model.Address;

/**
 * 地址列表
 */
public class AddressVO {

	
	public AddressVO() {
		super();
	}
	
	public AddressVO(Address address) {
		super();
		this.id = address.getId();
		this.lastTime = address.getLastTime();
		this.createTime = address.getCreateTime();
		this.transactionCount = address.getTransactions().size();
		this.assetCount = address.getAssets().size();
	}

	/**
     * 地址
     */
	private String id;
	
	/**
     * 最后交易时间
     */
    private Long lastTime;
    
    /**
     * 地址生成时间
     */
    private Long createTime;
    
    /**
     * 交易次数
     */
    private Integer transactionCount;

    /**
     * 资产数量
     */
    private Integer assetCount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getLastTime() {
		return lastTime;
	}

	public void setLastTime(Long lastTime) {
		this.lastTime = lastTime;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Integer getTransactionCount() {
		return transactionCount;
	}

	public void setTransactionCount(Integer transactionCount) {
		this.transactionCount = transactionCount;
	}

	public Integer getAssetCount() {
		return assetCount;
	}

	public void setAssetCount(Integer assetCount) {
		this.assetCount = assetCount;
	}
    
}
