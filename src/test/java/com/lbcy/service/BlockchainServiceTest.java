package com.lbcy.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by 吴晓冬 on 2017/9/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BlockchainServiceTest
{
    @Autowired
    private BlockchainService blockchainService;

    @Test
    public void getBlockInfoFromBlockchain() throws Exception
    {
        blockchainService.getBlockFromBlockchain((long)29942);
    }

//    @Test
    public void getMaxBlockIdFromBlockchain() throws Exception
    {
        blockchainService.getMaxBlockIdFromBlockchain();
    }

//    @Test
    public void findAndSave() throws Exception
    {
        blockchainService.findAndSave((long)29942);
    }
}