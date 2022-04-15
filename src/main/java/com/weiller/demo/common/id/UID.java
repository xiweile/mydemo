package com.weiller.demo.common.id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UID {
    private static IdGenerator idGenerator;

    public UID() {
    }

    @Autowired
    public void setIdGenerator(IdGenerator idGenerator) {
        UID.idGenerator = idGenerator;
    }

    public static long get() {
        return idGenerator.getLong();
    }

    public static int getWorkerId() {
        return idGenerator.getWorkerId();
    }
}
