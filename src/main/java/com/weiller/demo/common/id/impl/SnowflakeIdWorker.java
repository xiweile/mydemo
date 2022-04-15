package com.weiller.demo.common.id.impl;

import java.util.Date;
import java.util.Random;

public class SnowflakeIdWorker {
    private static final long twepoch = 1483200000000L;
    private static final long workerIdBits = 7L;
    private static final long datacenterIdBits = 3L;
    public static final int maxWorkerId = 127;
    private static final int maxDatacenterId = 7;
    private static final long sequenceBits = 12L;
    private final long workerIdShift = 12L;
    private final long datacenterIdShift = 19L;
    private static final long timestampLeftShift = 22L;
    private final long sequenceMask = 4095L;
    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;
    Random random = new Random();

    public SnowflakeIdWorker(long workerId, long datacenterId) {
        if (workerId <= 127L && workerId >= 0L) {
            if (datacenterId <= 7L && datacenterId >= 0L) {
                this.workerId = workerId;
                this.datacenterId = datacenterId;
            } else {
                throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", 7));
            }
        } else {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", 127));
        }
    }

    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (timestamp < this.lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", this.lastTimestamp - timestamp));
        } else {
            if (this.lastTimestamp == timestamp) {
                this.sequence = this.sequence + 1L & 4095L;
                if (this.sequence == 0L) {
                    timestamp = this.tilNextMillis(this.lastTimestamp);
                }
            } else {
                this.sequence = (long)this.random.nextInt(10);
            }

            this.lastTimestamp = timestamp;
            return timestamp - 1483200000000L << 22 | this.datacenterId << 19 | this.workerId << 12 | this.sequence;
        }
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp;
        for(timestamp = this.timeGen(); timestamp <= lastTimestamp; timestamp = this.timeGen()) {
        }

        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    public static int extractWorkerId(long snowflakeId) {
        long mask = 520192L;
        return (int)((snowflakeId & mask) >> 12);
    }

    public static long getTimestamps(long snowflakeId) {
        long mask = 9223372036850581504L;
        return ((snowflakeId & mask) >> 22) + 1483200000000L;
    }

    public static Date getTSDate(long snowflakeId) {
        return new Date(getTimestamps(snowflakeId));
    }

    public static int getMaxWorkerId() {
        return 127;
    }

    public void setWorkerId(long workerId) {
        if (workerId >= 0L && workerId <= 127L) {
            this.workerId = workerId;
        } else {
            throw new RuntimeException(String.format("WorkerId must between %d and %d.", 0, 127));
        }
    }

    public void setDataCenterId(int dataCenterId) {
        if (dataCenterId >= 0 && dataCenterId <= 7) {
            this.datacenterId = (long)dataCenterId;
        } else {
            throw new RuntimeException(String.format("DataCenterId must between %d and %d.", 0, 7));
        }
    }
}
