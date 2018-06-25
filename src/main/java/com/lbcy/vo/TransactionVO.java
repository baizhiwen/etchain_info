package com.lbcy.vo;

import java.util.List;
import java.util.Map;

import com.lbcy.model.Transaction;
import com.lbcy.utils.HexUtils;

/**
 * 交易列表数据
 */
public class TransactionVO {
	
	public TransactionVO() {
		super();
	}

	public TransactionVO(Transaction transaction) {
		super();
		this.txType = transaction.getTxType();
		this.hash = transaction.getHash();
		this.blockId = transaction.getBlockId();
		this.timestamp = transaction.getTimestamp();
		this.utxoInputCount = transaction.getUtxoInputs().size();
		this.outputCount = transaction.getOutputs().size();
		this.attributes = transaction.getAttributes();
		for (Map<String, Object> map : attributes) {
			Object obj = map.get("Usage");
			if(obj == null || !(obj instanceof Double)) {
				continue;
			}
			Double key = (Double)obj;
			if(key.equals(0D)) {
				continue;
			}
			Object value = map.get("Data");
			if(value instanceof String) {
				map.put("Data", HexUtils.hexDecode((String)value));
			}
		}
	}

	private Integer txType;
	
	private String hash;
	
	private Long blockId;
	
	private Long timestamp;
	
	private Integer utxoInputCount;
	
	private Integer outputCount;
	
	private List<Map<String, Object>> attributes;

	public Integer getTxType() {
		return txType;
	}

	public void setTxType(Integer txType) {
		this.txType = txType;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Long getBlockId() {
		return blockId;
	}

	public void setBlockId(Long blockId) {
		this.blockId = blockId;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getUtxoInputCount() {
		return utxoInputCount;
	}

	public void setUtxoInputCount(Integer utxoInputCount) {
		this.utxoInputCount = utxoInputCount;
	}

	public Integer getOutputCount() {
		return outputCount;
	}

	public void setOutputCount(Integer outputCount) {
		this.outputCount = outputCount;
	}

	public List<Map<String, Object>> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Map<String, Object>> attributes) {
		this.attributes = attributes;
	}
}
