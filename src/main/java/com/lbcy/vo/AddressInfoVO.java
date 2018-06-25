package com.lbcy.vo;

import java.util.ArrayList;
import java.util.List;

import com.lbcy.model.Address;
import com.lbcy.model.Address.Asset;
import com.lbcy.model.Transaction;

/**
 * 地址明细
 */
public class AddressInfoVO extends AddressVO {

	public AddressInfoVO() {
		super();
	}


	public AddressInfoVO(Address address) {
		super(address);
		this.transactions = new ArrayList<TransactionInfoVO>();
		for (Transaction transaction : address.getTransactions()) {
			this.transactions.add(new TransactionInfoVO(transaction));
		}
		this.assets = address.getAssets();
	}

	private List<TransactionInfoVO> transactions;
	
	private List<Asset> assets;

	private Integer total;

	private Integer pages;

	private Integer currentPage;
	
	public List<TransactionInfoVO> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionInfoVO> transactions) {
		this.transactions = transactions;
	}

	public List<Asset> getAssets() {
		return assets;
	}

	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
}
