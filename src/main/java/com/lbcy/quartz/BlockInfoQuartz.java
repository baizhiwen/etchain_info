package com.lbcy.quartz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lbcy.service.BlockchainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by 吴晓冬 on 2017/9/19.
 */
@Component
public class BlockInfoQuartz
{
    @Autowired
    private BlockchainService blockchainService;

    private final static Logger logger = LoggerFactory.getLogger(BlockInfoQuartz.class);

    /**
     * 区块信息入库定时器
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void getBlockInfoToMongo() throws Exception
    {
        logger.info("getBlockInfoToMongo start");

        long dbBlockId = blockchainService.getMaxBlockIdFromDb();

        long chainBlociId = blockchainService.getMaxBlockIdFromBlockchain();

        for (long blockId = dbBlockId+1; blockId <chainBlociId; blockId++)
        {
            try
            {
                blockchainService.findAndSave(blockId);
            }
            catch (Throwable e)
            {
                logger.error("getBlockInfoToMongo fail blockId is {}", blockId, e);
                return;
            }
        }

        logger.info("getBlockInfoToMongo finish");
    }

    /**
     * 异常监控
     * 上一区块时间距离现在超过10分钟就预警
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void monitorBlock()
    {

    }
}
