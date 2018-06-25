package com.lbcy.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lbcy.model.Transaction;
import com.lbcy.model.Transaction.UTXOInput;
import com.lbcy.model.TransactionOutput;

/**
 * 交易明细数据
 */
public class TransactionInfoVO extends TransactionVO {

	
	public TransactionInfoVO() {
		super();
	}

	public TransactionInfoVO(Transaction transaction) {
		super(transaction);
		this.utxoInputs = new ArrayList<UTXOInputVO>();
		this.outputs = new ArrayList<UTXOInputVO>();
		for (UTXOInput utxoInput : transaction.getUtxoInputs()) {
			this.utxoInputs.add(new UTXOInputVO(utxoInput));
		}
		for (TransactionOutput transactionOutput : transaction.getOutputs()) {
			this.outputs.add(new UTXOInputVO(transactionOutput));
		}
	}


	public List<UTXOInputVO> utxoInputs;
	public List<UTXOInputVO> outputs;

	public List<UTXOInputVO> getUtxoInputs() {
		return utxoInputs;
	}

	public void setUtxoInputs(List<UTXOInputVO> utxoInputs) {
		this.utxoInputs = utxoInputs;
	}
	
	public List<UTXOInputVO> getOutputs() {
		return outputs;
	}

	public void setOutputs(List<UTXOInputVO> outputs) {
		this.outputs = outputs;
	}

	public class UTXOInputVO {

		public UTXOInputVO() {
			super();
		}
		
		public UTXOInputVO(UTXOInput utxoInput) {
			super();
			this.address = utxoInput.getOutput().getAddress();
			this.value = utxoInput.getOutput().getValue();
			this.name = utxoInput.getOutput().getAsset().getName();
			this.valueString = utxoInput.getOutput().getValueString();
		}
		
		public UTXOInputVO(TransactionOutput transactionOutput) {
			super();
			this.address = transactionOutput.getAddress();
			this.value = transactionOutput.getValue();
			this.name = transactionOutput.getAsset().getName();
			this.valueString = transactionOutput.getValueString();
		}

		private String address;

		private BigDecimal value;

		private String name;

		private String valueString;

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public BigDecimal getValue() {
			return value;
		}

		public void setValue(BigDecimal value) {
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValueString() {
			return valueString;
		}

		public void setValueString(String valueString) {
			this.valueString = valueString;
		}
	}

}
