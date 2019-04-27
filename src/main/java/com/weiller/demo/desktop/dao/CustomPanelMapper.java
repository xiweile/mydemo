package com.weiller.demo.desktop.dao;

import com.weiller.demo.desktop.entity.CustomPanel;
import com.weiller.demo.desktop.entity.PanelDetails;

import java.util.List;

public interface CustomPanelMapper {
    void deleteByPrimaryKey(String id);

    void insertSelective(CustomPanel record);

    CustomPanel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CustomPanel record);

    List<PanelDetails> findCustomPanelsByUserId(String userId);

    PanelDetails getCustomPanel(String id);

    void deleteByUserId(String userId);

    void deleteByPanelId(Integer id);

}