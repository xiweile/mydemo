package com.weiller.demo.common.id.config;

import com.weiller.demo.common.id.IdGenerator;
import com.weiller.demo.common.id.WorkerIdGenerator;
import com.weiller.demo.common.id.impl.SnowflakeIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({IdGenerator.class})
@EnableConfigurationProperties({UidProperties.class})
public class UidAutoConfigure {
    private Logger logger = LoggerFactory.getLogger(UidAutoConfigure.class);

    public UidAutoConfigure() {
    }

    @Bean
    @ConditionalOnMissingBean
    IdGenerator idGenerator(WorkerIdGenerator workerIdGenerator, UidProperties properties) {
        this.logger.info("Init SnowflakeIdGenerator, dataCenterId: {}, workId: {}", properties.getDataCenterId(), properties.getWorkerId());
        return new SnowflakeIdGenerator(properties.getDataCenterId(), properties.getWorkerId(), workerIdGenerator);
    }
}
