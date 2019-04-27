package com.weiller.demo.hello.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Data
@Component
@ConfigurationProperties(prefix = "user")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String password;

    @Transient
    private Map<String,Object> info;

    @Transient
    private List<String> role;


}
