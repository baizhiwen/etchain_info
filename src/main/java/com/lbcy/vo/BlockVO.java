package com.lbcy.vo;

import com.lbcy.model.Block;
import com.lbcy.model.Block.BlockData;

/**
 * 区块列表数据
 */
public class BlockVO {
	
	
	public BlockVO() {
		super();
	}

	public BlockVO(Block block) {
		super();
		this.id = block.getId();
		this.blockData = new BlockDataVO(block.getBlockData());
		this.transactionCount = block.getTransactions().size();
		this.hash = block.getHash();
	}

	private Long id;
	
	private BlockDataVO blockData;
	
	private Integer transactionCount;
	
	private String hash;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BlockDataVO getBlockData() {
		return blockData;
	}

	public void setBlockData(BlockDataVO blockData) {
		this.blockData = blockData;
	}

	public Integer getTransactionCount() {
		return transactionCount;
	}

	public void setTransactionCount(Integer transactionCount) {
		this.transactionCount = transactionCount;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}


	class BlockDataVO {
		
		public BlockDataVO() {
			super();
		}
		public BlockDataVO(BlockData blockData) {
			super();
			this.timestamp = blockData.getTimestamp();
			this.transactionsRoot = blockData.getTransactionsRoot();
			this.prevBlockHash = blockData.getPrevBlockHash();
			this.hash = blockData.getHash();
		}

		private Long timestamp;
		private String transactionsRoot;
		private String prevBlockHash;
        private String hash;
		public Long getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(Long timestamp) {
			this.timestamp = timestamp;
		}
		public String getTransactionsRoot() {
			return transactionsRoot;
		}
		public void setTransactionsRoot(String transactionsRoot) {
			this.transactionsRoot = transactionsRoot;
		}
		public String getPrevBlockHash() {
			return prevBlockHash;
		}
		public void setPrevBlockHash(String prevBlockHash) {
			this.prevBlockHash = prevBlockHash;
		}
		public String getHash() {
			return hash;
		}
		public void setHash(String hash) {
			this.hash = hash;
		}
	}
}
