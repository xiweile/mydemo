package com.weiller.demo.common.id;

public interface WorkerIdGenerator {
    int getWorkerId(WorkerIdGenerator.WorkerIdCallback var1);

    public interface WorkerIdCallback {
        void callback(int var1);
    }
}
