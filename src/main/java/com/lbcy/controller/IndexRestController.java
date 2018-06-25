package com.lbcy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lbcy.utils.HexUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lbcy.model.Address;
import com.lbcy.model.Block;
import com.lbcy.model.Transaction;
import com.lbcy.vo.AddressInfoVO;
import com.lbcy.vo.AddressVO;
import com.lbcy.vo.BlockInfoVO;
import com.lbcy.vo.BlockVO;
import com.lbcy.vo.TransactionInfoVO;
import com.lbcy.vo.TransactionVO;

@RestController
@RequestMapping("/api")
public class IndexRestController {

	//最大分页记录数
	private int maxPageSize = 50;
	
	@Autowired
    private MongoTemplate mongoTemplate;
	
	/**
	 * 查询首页信息
	 */
	@GetMapping("/info")
	public Object info() {
		
		Map<String,Object> map = new HashMap<>();
		
		//获取区块信息
        Query blockQuery = new Query().with(new Sort(Sort.Direction.DESC, "_id")).limit(5);
        List<Block> blocks = mongoTemplate.find(blockQuery, Block.class);
        List<BlockVO> blockVOs = new ArrayList<>();
        for (Block block : blocks) {
        	BlockVO blockVO = new BlockVO(block);
        	blockVOs.add(blockVO);
		}
        map.put("blocks", blockVOs);

        //获取交易信息
        Query txQuery = new Query(Criteria.where("txType").ne(0)).with(new Sort(Sort.Direction.DESC, "timestamp")).limit(5);
        List<Transaction> transactions = mongoTemplate.find(txQuery, Transaction.class);
        List<TransactionVO> transactionVOs = new ArrayList<TransactionVO>();
        for (Transaction transaction : transactions) {
			TransactionVO transactionVO = new TransactionVO(transaction);
			transactionVOs.add(transactionVO);
		}
        map.put("transactions", transactionVOs);

        //获取地址信息
        Query addressQuery = new Query().with(new Sort(Sort.Direction.DESC, "lastTime")).limit(5);
        List<Address> addresses = mongoTemplate.find(addressQuery, Address.class);
        map.put("addresses", addresses);
        List<AddressVO> addressVOs = new ArrayList<AddressVO>();
        for (Address address : addresses) {
        	addressVOs.add(new AddressVO(address));
		}
        map.put("addresses", addressVOs);
        
		return map;
	}
	
	/**
     * 区块信息分页展示
     */
    @GetMapping("/blocks/{pageSize}/{pageNumber}")
    public Object block(@PathVariable("pageNumber")Integer pageNumber, @PathVariable("pageSize")Integer pageSize) {
        
    	if(pageSize > maxPageSize) {
    		return "pageSize is too big!";
    	}
    	Map<String,Object> map = new HashMap<>();
        map.put("currentPage", pageNumber);
        
        Query query = new Query().with(new Sort(Sort.Direction.DESC, "_id"));
        long count = mongoTemplate.count(query, Block.class);
        long pages = (count + pageSize -1 ) / pageSize;
        map.put("pages", pages);
        map.put("total", count);

        query.skip((pageNumber-1)*pageSize).limit(pageSize);
        List<Block> blocks = mongoTemplate.find(query, Block.class);
        
        List<BlockVO> list = new ArrayList<>();
        for (Block block : blocks) {
        	BlockVO blockVO = new BlockVO(block);
        	list.add(blockVO);
		}
        map.put("blocks", list);
        return map;
    }
    
    /**
     * 区块明细展示
     */
    @GetMapping("/block/{blockId}")
    public Object blockDetail(@PathVariable("blockId")Long blockId) {
    	Block block = mongoTemplate.findById(blockId, Block.class);
    	if (block == null) {
			return null;
		}
    	return new BlockInfoVO(block);
    }
    
    /**
     * 交易信息分页展示
     */
    @GetMapping("/txs/{pageSize}/{pageNumber}")
    public Object tx(@PathVariable("pageNumber")Integer pageNumber, @PathVariable("pageSize")Integer pageSize) {

    	if(pageSize > maxPageSize) {
    		return "pageSize is too big!";
    	}
    	Map<String,Object> map = new HashMap<>();
        map.put("currentPage", pageNumber);

        Query query = new Query(Criteria.where("txType").ne(0)).with(new Sort(Sort.Direction.DESC, "timestamp"));
        long count = mongoTemplate.count(query, Transaction.class);
        long pages = (count + pageSize -1 ) / pageSize;
        map.put("pages", pages);
        map.put("total", count);

        query.skip((pageNumber-1)*pageSize).limit(pageSize);
        List<Transaction> transactions = mongoTemplate.find(query, Transaction.class);
        
        List<TransactionVO> list = new ArrayList<TransactionVO>();
        for (Transaction transaction : transactions) {
			TransactionVO transactionVO = new TransactionVO(transaction);
			list.add(transactionVO);
		}
        map.put("transactions", list);
        return map;
    }
    
    /**
     * 交易明细展示
     */
    @GetMapping("/tx/{hash}")
    public Object txDetail(@PathVariable("hash") String hash) {
    	 Transaction transaction = mongoTemplate.findById(hash, Transaction.class);
    	 if(transaction == null) {
    		 return null;
    	 }
    	 return new TransactionInfoVO(transaction);
    }

    /**
     * 地址信息分页展示
     */
    @GetMapping("/addresses/{pageSize}/{pageNumber}")
    public Object address(@PathVariable("pageNumber")Integer pageNumber, @PathVariable("pageSize")Integer pageSize) {
    	
    	if(pageSize > maxPageSize) {
    		return "pageSize is too big!";
    	}
    	Map<String,Object> map = new HashMap<>();
        map.put("currentPage", pageNumber);

        Query query = new Query().with(new Sort(Sort.Direction.DESC, "lastTime"));
        long count = mongoTemplate.count(query, Address.class);
        long pages = (count + pageSize -1 ) / pageSize;
        map.put("pages", pages);
        map.put("total", count);

        query.skip((pageNumber-1)*pageSize).limit(pageSize);
        List<Address> addresses = mongoTemplate.find(query, Address.class);
        
        List<AddressVO> list = new ArrayList<AddressVO>();
        for (Address address : addresses) {
        	list.add(new AddressVO(address));
		}
        map.put("addresses", list);

        return map;
    }
    /**
     * 地址明细展示
     */
    @GetMapping("/address/{id}")
    public Object addressDetail(@PathVariable("id")String id) {
    	 Address address = mongoTemplate.findById(id, Address.class);
    	 if (address == null) {
    		AddressInfoVO addressInfoVO = new AddressInfoVO();
    		addressInfoVO.setId(id);
    		addressInfoVO.setAssetCount(0);
    		addressInfoVO.setTransactionCount(0);
    		addressInfoVO.setAssets(new ArrayList<>());
    		addressInfoVO.setTransactions(new ArrayList<>());
			return addressInfoVO;
    	 }
    	 return new AddressInfoVO(address);
    }

	@GetMapping("/address/{id}/{pageSize}/{pageNumber}")
	public Object addressDetail(@PathVariable("id")String id,@PathVariable("pageNumber")Integer pageNumber, @PathVariable("pageSize")Integer pageSize) {
		if(pageSize > maxPageSize) {
			return "pageSize is too big!";
		}

    	Address address = mongoTemplate.findById(id, Address.class);
		if (address == null) {
			AddressInfoVO addressInfoVO = new AddressInfoVO();
			addressInfoVO.setId(id);
			addressInfoVO.setAssetCount(0);
			addressInfoVO.setTransactionCount(0);
			addressInfoVO.setAssets(new ArrayList<>());
			addressInfoVO.setTransactions(new ArrayList<>());
			addressInfoVO.setCurrentPage(pageNumber);
			addressInfoVO.setTotal(0);
			addressInfoVO.setPages(1);
			return addressInfoVO;
		}else {
			Integer count = address.getTransactions().size();
			Integer pages = (count + pageSize -1 ) / pageSize;
			List<Transaction> transactions = address.getTransactions();
			Integer start = (pageNumber-1)*pageSize;
			Integer end = start + pageSize;
			if (count < start + 1){
				transactions = new ArrayList<>();
			}else if (start + 1 <= count && count < end ){
				transactions = transactions.subList(start,count);
			}else{
				transactions = transactions.subList(start,end);
			}
			address.setTransactions(transactions);
			AddressInfoVO addressInfoVO = new AddressInfoVO(address);
			addressInfoVO.setTotal(count);
			addressInfoVO.setCurrentPage(pageNumber);
			addressInfoVO.setPages(pages);
			return addressInfoVO;
		}
	}


    /**
     * 根据区块高度、区块Hash，交易Hash，地址来查询
     * 
     * @return
     *  type, 1:区块; 2:交易; 3:地址 
     *  	-1:错误的搜索内容(不是地址,hash和块高); -2:未找到对应的hash数据; -3:高度不存在
     */
    @GetMapping("/search")
    public Object search(@RequestParam("value")String value) {
        
    	if(value == null || (value = value.trim()).isEmpty()) {
    		return null;
    	}
    	Query query = null;
    	Map<String,Object> map = new HashMap<>();
        
        //判断是否是地址
        if(value.length() == 34) {
			query = new Query(Criteria.where("_id").is(value));
			Address address = mongoTemplate.findOne(query, Address.class);
			AddressInfoVO addressInfoVO = null;
			if (address != null) {
				addressInfoVO = new AddressInfoVO(address);
			}else {
				addressInfoVO = new AddressInfoVO();
	    		addressInfoVO.setId(value);
	    		addressInfoVO.setAssetCount(0);
	    		addressInfoVO.setTransactionCount(0);
	    		addressInfoVO.setAssets(new ArrayList<>());
	    		addressInfoVO.setTransactions(new ArrayList<>());
			}
			map.put("type", 3);
			map.put("address", addressInfoVO);
			return map;
        }
        
        //判断是否区块Hash或者交易Hash
        if(value.length() == 64) {
	        query = new Query(Criteria.where("hash").is(value));
	        Block block = mongoTemplate.findOne(query, Block.class);
	        if (null != block) {
	        	map.put("type", 1);
	        	map.put("block", new BlockInfoVO(block));
	            return map;
	        }
	        //判断是否是交易Hash
	        query = new Query(Criteria.where("_id").is(value));
	        Transaction transaction = mongoTemplate.findOne(query, Transaction.class);
	        if (null != transaction) {
	        	map.put("type", 2);
	        	map.put("transaction", new TransactionInfoVO(transaction));
	        	return map;
	        }
	        map.put("type", -2);
	        return map;
        }

        //判断是否是区块高度
        Long blockId = null;
        try {
            blockId = Long.parseLong(value);
            query = new Query(Criteria.where("_id").is(blockId));
            Block block = mongoTemplate.findOne(query, Block.class);
            if (block == null) {
            	map.put("type", -3);
    	        return map;
            }
            BlockInfoVO blockInfoVO = new BlockInfoVO(block);
            map.put("type", 1);
            map.put("block", blockInfoVO);
            return map;
        } catch (NumberFormatException e){}

        map.put("type", -1);
        return map;
    }


	/**
	 * 地址商品详情展示
	 */
	@GetMapping("/address/product/{id}")
	public Object addressProductDetail(@PathVariable("id")String id) {
		Address proAddrObj = mongoTemplate.findById(id, Address.class);
		JSONObject jsonObject = new JSONObject();
		String batchAddr = "";
		jsonObject.put("status","NO_PRODUCET");
		if (proAddrObj != null) {
			List<Transaction> proTxList = proAddrObj.getTransactions();
			for (Transaction transaction : proTxList){
				List<Map<String, Object>> attrs = transaction.getAttributes();
				for (Map<String, Object> map : attrs){
					String usage = String.valueOf(map.get("Usage"));
					if ("144.0".equals(usage)){
						JSONObject dataJson = JSONObject.fromObject(HexUtils.hexDecode((String)map.get("Data")));
						String batchType = dataJson.getString("type");
						if ("2".equals(batchType)){
							batchAddr = dataJson.getString("address");
						}
					}
				}
			}

			if (StringUtils.isNotBlank(batchAddr)) {
				Address batchAddrObj = mongoTemplate.findById(batchAddr, Address.class);
				List<Transaction> batchTxList = batchAddrObj.getTransactions();
				JSONArray pubStepArr = new JSONArray();
				for (Transaction transaction : batchTxList) {
					List<Map<String, Object>> attrs = transaction.getAttributes();
					for (Map<String, Object> map : attrs) {
						String usage = String.valueOf(map.get("Usage"));
						if ("144.0".equals(usage)) {
							JSONObject dataJson = JSONObject.fromObject(HexUtils.hexDecode((String)map.get("Data")));
							String batchType = dataJson.getString("type");
							if ("1".equals(batchType)) {
								jsonObject.put("map",dataJson.getJSONObject("map"));
							}
							if ("3".equals(batchType)) {
								JSONObject mapJson = dataJson.getJSONObject("map");
								JSONObject publishStep = mapJson.getJSONObject("publishStep");
								pubStepArr.add(publishStep);
								jsonObject.put("pubStepArr",pubStepArr);
							}
						}
					}
				}
				jsonObject.put("status","SUCCESS");
			}
		}
		return jsonObject;
	}
}
