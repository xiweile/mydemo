package com.weiller.demo.desktop.dao;

import com.weiller.demo.desktop.entity.DmPanel;

import java.util.List;

public interface DmPanelMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(DmPanel record);

    DmPanel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DmPanel record);

    List<DmPanel> findAll();

}