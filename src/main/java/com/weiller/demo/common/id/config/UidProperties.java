package com.weiller.demo.common.id.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("system.uid")
public class UidProperties {
    private int dataCenterId = 0;
    private int workerId = -1;
    private boolean failOnGetWorkerIdError = false;

    public UidProperties() {
    }

    public int getDataCenterId() {
        return this.dataCenterId;
    }

    public void setDataCenterId(int dataCenterId) {
        this.dataCenterId = dataCenterId;
    }

    public int getWorkerId() {
        return this.workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public boolean isFailOnGetWorkerIdError() {
        return this.failOnGetWorkerIdError;
    }

    public void setFailOnGetWorkerIdError(boolean failOnGetWorkerIdError) {
        this.failOnGetWorkerIdError = failOnGetWorkerIdError;
    }
}
