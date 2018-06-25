package com.lbcy.model;

import com.google.gson.annotations.SerializedName;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * Created by 吴晓冬 on 2017/9/20.
 */
@Document(collection="dna_block")
public class Block
{
    @Id
    private Long id;

    @SerializedName("Hash")
    private String hash;

    @SerializedName("BlockData")
    private BlockData blockData;

    @DBRef(lazy=true)
    @SerializedName("Transactions")
    private List<Transaction> transactions;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getHash()
    {
        return hash;
    }

    public void setHash(String hash)
    {
        this.hash = hash;
    }

    public BlockData getBlockData()
    {
        return blockData;
    }

    public void setBlockData(BlockData blockData)
    {
        this.blockData = blockData;
    }

    public List<Transaction> getTransactions()
    {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions)
    {
        this.transactions = transactions;
    }

    public class BlockData
    {
        @SerializedName("Version")
        private Integer version;

        @SerializedName("PrevBlockHash")
        private String prevBlockHash;

        @SerializedName("TransactionsRoot")
        private String transactionsRoot;

        @SerializedName("Timestamp")
        private Long timestamp;

        @SerializedName("Height")
        private Long height;

        @SerializedName("ConsensusData")
        private BigInteger consensusData;

        @SerializedName("NextBookKeeper")
        private String nextBookKeeper;

        @SerializedName("Program")
        private Map<String, Object> program;

        @SerializedName("Hash")
        private String hash;

        public Integer getVersion()
        {
            return version;
        }

        public void setVersion(Integer version)
        {
            this.version = version;
        }

        public String getPrevBlockHash()
        {
            return prevBlockHash;
        }

        public void setPrevBlockHash(String prevBlockHash)
        {
            this.prevBlockHash = prevBlockHash;
        }

        public String getTransactionsRoot()
        {
            return transactionsRoot;
        }

        public void setTransactionsRoot(String transactionsRoot)
        {
            this.transactionsRoot = transactionsRoot;
        }

        public Long getTimestamp()
        {
            return timestamp;
        }

        public void setTimestamp(Long timestamp)
        {
            this.timestamp = timestamp;
        }

        public Long getHeight()
        {
            return height;
        }

        public void setHeight(Long height)
        {
            this.height = height;
        }

        public BigInteger getConsensusData()
        {
            return consensusData;
        }

        public void setConsensusData(BigInteger consensusData)
        {
            this.consensusData = consensusData;
        }

        public String getNextBookKeeper()
        {
            return nextBookKeeper;
        }

        public void setNextBookKeeper(String nextBookKeeper)
        {
            this.nextBookKeeper = nextBookKeeper;
        }

        public Map<String, Object> getProgram()
        {
            return program;
        }

        public void setProgram(Map<String, Object> program)
        {
            this.program = program;
        }

        public String getHash()
        {
            return hash;
        }

        public void setHash(String hash)
        {
            this.hash = hash;
        }
    }
}
