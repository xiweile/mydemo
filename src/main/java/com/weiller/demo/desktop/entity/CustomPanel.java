package com.weiller.demo.desktop.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CustomPanel {
    private String id;

    private String userId;

    private Integer panelId;

    private Integer x;

    private Integer y;

    private Integer width;

    private Integer height;

    private String yxbz;

    private Date createTime;
}