package com.lbcy.vo;

import java.util.ArrayList;
import java.util.List;

import com.lbcy.model.Block;
import com.lbcy.model.Transaction;

/**
 * 区块明细数据
 */
public class BlockInfoVO extends BlockVO {
	
	
	public BlockInfoVO() {
		super();
	}

	public BlockInfoVO(Block block) {
		super(block);
		this.transactions = new ArrayList<TransactionInfoVO>();
		for (Transaction transaction : block.getTransactions()) {
			this.transactions.add(new TransactionInfoVO(transaction));
		}
	}

	List<TransactionInfoVO> transactions;

	public List<TransactionInfoVO> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionInfoVO> transactions) {
		this.transactions = transactions;
	}
	
}
