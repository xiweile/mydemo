package com.weiller.demo.common.id.impl;

import com.weiller.demo.common.id.WorkerIdGenerator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class WokerIdLocalIPGenarator implements WorkerIdGenerator {
    public WokerIdLocalIPGenarator() {
    }

    public int getWorkerId(WorkerIdCallback callback) {
        int workerId = (new Random()).nextInt(127);
        return workerId;
    }
}
