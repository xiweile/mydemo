package com.weiller.demo.common.id.impl;

import java.util.Random;

import com.weiller.demo.common.id.IdGenerator;
import com.weiller.demo.common.id.WorkerIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SnowflakeIdGenerator implements IdGenerator {
    private static final Logger logger = LoggerFactory.getLogger(SnowflakeIdGenerator.class);
    private static final int DEFAULT_DATACENTER_ID = 0;
    private static final int DEFAULT_WORKER_ID = -1;
    private int workerId = -1;
    private int dataCenterId = 0;
    protected SnowflakeIdWorker snowflakeIdWorker;
    protected SnowflakeIdGenerator workIdGenerator;

    public SnowflakeIdGenerator() {
        this.initIdWorker();
    }

    public SnowflakeIdGenerator(int dataCenterId, int workerId) {
        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
        this.initIdWorker();
    }

    public SnowflakeIdGenerator(int dataCenterId, int workerId, WorkerIdGenerator workIdGenerator) {
        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
        int genWorkerId = workIdGenerator.getWorkerId((workerId1) -> {
            this.workerId = workerId1;
            this.snowflakeIdWorker.setWorkerId((long)workerId1);
        });
        if (genWorkerId >= 0) {
            this.workerId = genWorkerId;
        }

        this.initIdWorker();
    }

    public void initIdWorker() {
        if (this.workerId == -1) {
            this.workerId = this.generateDefaultWorkerId();
        }

        logger.info("workerId:{}", this.workerId);
        this.snowflakeIdWorker = new SnowflakeIdWorker((long)this.workerId, (long)this.dataCenterId);
    }

    private int generateDefaultWorkerId() {
        return (new Random()).nextInt(SnowflakeIdWorker.getMaxWorkerId());
    }

    public long getLong() {
        return this.snowflakeIdWorker.nextId();
    }

    public int getWorkerId() {
        return this.workerId;
    }
}
