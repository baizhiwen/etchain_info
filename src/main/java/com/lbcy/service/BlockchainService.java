package com.lbcy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.lbcy.model.Block;
import com.lbcy.model.BlockchainResponse;
import com.lbcy.model.Transaction;
import com.lbcy.service.transaction.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by 吴晓冬 on 2017/9/19.
 */
@Service
public class BlockchainService
{
    @Value("${blockchain.url}")
    private String blockchainUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private Map<String, TransactionService> transactionServiceMap;

    private final static Logger logger = LoggerFactory.getLogger(BlockchainService.class);

    /**
     * 根据区块编号获取区块信息
     * @param blockId 区块编号
     */
    public Block getBlockFromBlockchain(Long blockId) throws JsonProcessingException
    {
        BlockchainResponse blockchainResponse = restTemplate.getForObject(blockchainUrl + "/api/v1/block/details/height/{blockId}", BlockchainResponse.class, blockId);

        if (0 != blockchainResponse.getError())
        {
            logger.error("getBlockInfoFromBlockchain fail blockchainResponse is {}", new ObjectMapper().writeValueAsString(blockchainResponse));

            return null;
        }

        return blockchainResponse.getResult();
    }

    /**
     * 获取区块链上的最大区块编号
     */
    public Long getMaxBlockIdFromBlockchain() throws JsonProcessingException
    {
        long height = 0;

        Map<String, Object> heightInfo = restTemplate.getForObject(blockchainUrl + "/api/v1/block/height", Map.class);

        if (0 != (Double)heightInfo.get("Error"))
        {
            logger.error("getMaxBlockIdFromBlockchain fail heightInfo is {}", new ObjectMapper().writeValueAsString(heightInfo));

            return height;
        }

        if (heightInfo.get("Result") instanceof Integer)
        {
            height = new Long((Integer) heightInfo.get("Result"));
        }
        else if (heightInfo.get("Result") instanceof Long)
        {
            height = (Long) heightInfo.get("Result");
        }
        else if (heightInfo.get("Result") instanceof Double)
        {
            height = ((Double) heightInfo.get("Result")).longValue();
        }
        return height;
    }

    /**
     * 获取数据库中最大区块编号
     */
    public Long getMaxBlockIdFromDb()
    {
        Query query = new Query().with(new Sort(Sort.Direction.DESC, "_id"));
        Block block = mongoTemplate.findOne(query, Block.class);

        if (null == block)
        {
            return new Long(-1);
        }
        else
        {
            return block.getId();
        }
    }

    public boolean findAndSave(long blockId) throws JsonProcessingException
    {
        Block block = getBlockFromBlockchain(blockId);

        if (null == block)
        {
            return false;
        }

        block.setId(blockId);

        List<Transaction> transactions = block.getTransactions();
        for (Transaction transaction : transactions)
        {
            transaction.setBlockId(blockId);
            transaction.setTimestamp(block.getBlockData().getTimestamp());

            TransactionService transactionService = null;
            if (transaction.getTxType() == 0)//记账交易
            {
                transactionService = transactionServiceMap.get("minerTransactionService");
            }
            else if (transaction.getTxType() == 64)//资产注册
            {
                transactionService = transactionServiceMap.get("registerTransactionService");
            }
            else if (transaction.getTxType() == 1)//资产发行
            {
                transactionService = transactionServiceMap.get("issueTransactionService");
            }
            else if (transaction.getTxType() == 128)//合约（转账）交易
            {
                transactionService = transactionServiceMap.get("contractTransactionService");
            }

            if (null == transactionService)
            {
                logger.error("TxType not find block is {}", new ObjectMapper().writeValueAsString(block));
                return false;
            }

            transactionService.deal(transaction);
        }

        mongoTemplate.save(block);

        logger.info("findAndSave block is {}", new ObjectMapper().writeValueAsString(block));

        return true;
    }

}