package com.luckyhua.webmvc.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author zhujuan
 * From: https://github.com/twitter/snowflake
 * An object that generates IDs.
 * This is broken into a separate class in case
 * we ever want to support multiple worker threads
 * per process
 */
public class IdWorker {
    
    protected static final Logger LOG = LoggerFactory.getLogger(IdWorker.class);
    
    private long workerId;
    private long datacenterId;
    private long sequence = 0L;

    private long twepoch = 1288834974657L;

    private long workerIdBits = 5L;
    private long datacenterIdBits = 5L;
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private long sequenceBits = 12L;

    private long workerIdShift = sequenceBits;
    private long datacenterIdShift = sequenceBits + workerIdBits;
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long lastTimestamp = -1L;

    public IdWorker(long workerId, long datacenterId) {
        // sanity check for workerId
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        LOG.info(String.format("worker starting. timestamp left shift %d, datacenter id bits %d, worker id bits %d, sequence bits %d, workerid %d", timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId));
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            LOG.error(String.format("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp));
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }
    
    public static void main(String[] args) {
		IdWorker id = new IdWorker(2,2);
		Random random = new Random(id.nextId());
		for(int i=0;i<10;i++){
//			check(id.nextId(), random);
			System.out.println(getCode());
		}
		System.out.println("success");
	}
    
    private static Set<String> idsSet = new HashSet<>(500000);
    
    private static boolean check(Long id,Random r){
    	String idS = id.toString();
    	int begin = r.nextInt(15);
    	String sub = idS.substring(begin, begin+1)+idS.substring(7, 8)+idS.substring(idS.length()-8,idS.length());
    	System.out.println(sub);
    	if(idsSet.contains(sub)){
    		throw new RuntimeException("size:"+idsSet.size());
    	}
    	
    	idsSet.add(sub);
    	
    	return true;
    }
    
    private static Random random = new Random(200);
    
    private static IdWorker idWorker = new IdWorker(30, 30);
    
    public static String getCode(){
    	String idS = idWorker.nextId()+"";
    	int begin = random.nextInt(15);
    	return idS.substring(begin, begin+1)+idS.substring(7, 8)+idS.substring(idS.length()-8,idS.length());
    }
}
