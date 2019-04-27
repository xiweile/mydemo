package com.weiller.demo.desktop.service;

import com.weiller.demo.desktop.entity.CustomPanel;
import com.weiller.demo.desktop.entity.DmPanel;
import com.weiller.demo.desktop.entity.PanelDetails;

import java.util.List;

/**
 * 自定义桌面服务接口
 */
public interface DesktopService {

    List<DmPanel> findAllDmPanel();

    List<PanelDetails> findCustomPanelsByUserId(String userId);

    PanelDetails getCustomPanel(String id);

    PanelDetails addCustomPanel(CustomPanel panel);

    void updateCustomPanel(String userId,List<CustomPanel> Panels);

    void deleteCustomPanel(String id);

    void deletePanel(Integer id);

    DmPanel saveOrUpdatePanel(DmPanel panel);

}
