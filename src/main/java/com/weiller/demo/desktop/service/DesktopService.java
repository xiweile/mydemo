package com.weiller.demo.desktop.service;

import com.weiller.demo.desktop.entity.PanelDetails;

import java.util.List;

/**
 * 自定义桌面服务接口
 */
public interface DesktopService {

    List<PanelDetails> findCustomPanelsByUserId();

    PanelDetails getCustomPanel(String id);

    void addCustomPanel(PanelDetails panel);

    void updateCustomPanel(List<PanelDetails> Panels);

    void deleteCustomPanel();
}
