package com.weiller.demo.desktop.service.impl;

import com.weiller.demo.desktop.dao.CustomPanelMapper;
import com.weiller.demo.desktop.dao.DmPanelMapper;
import com.weiller.demo.desktop.entity.CustomPanel;
import com.weiller.demo.desktop.entity.DmPanel;
import com.weiller.demo.desktop.entity.PanelDetails;
import com.weiller.demo.desktop.service.DesktopService;
import com.weiller.demo.utils.UuidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesktopServiceImpl implements DesktopService{

    @Autowired
    CustomPanelMapper customPanelMapper;

    @Autowired
    DmPanelMapper dmPanelMapper;

    @Override
    public List<DmPanel> findAllDmPanel() {
        return dmPanelMapper.findAll();
    }

    @Override
    public List<PanelDetails> findCustomPanelsByUserId(String userId) {
        return customPanelMapper.findCustomPanelsByUserId(userId);
    }

    @Override
    public PanelDetails getCustomPanel(String id) {
        return customPanelMapper.getCustomPanel(id);
    }

    @Override
    public PanelDetails addCustomPanel(CustomPanel panel) {
        panel.setId(UuidGenerator.getUUID());
        customPanelMapper.insertSelective(panel);
        return this.getCustomPanel(panel.getId());
    }

    @Override
    public void updateCustomPanel(String userId,List<CustomPanel> Panels) {
        // 删除该用户所有模板
        customPanelMapper.deleteByUserId(userId);
        // 从新添加
        Panels.forEach(p->{
            p.setUserId(userId);
            customPanelMapper.insertSelective(p);
        });

    }

    @Override
    public void deleteCustomPanel(String id) {
        customPanelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deletePanel(Integer id) {
        // 删除代码表数据
        dmPanelMapper.deleteByPrimaryKey(id);
        // 删除关联表
        customPanelMapper.deleteByPanelId(id);
    }

    @Override
    public DmPanel saveOrUpdatePanel(DmPanel panel) {
        if(panel.getId()!=null){
            dmPanelMapper.updateByPrimaryKeySelective(panel);
        }else{
            dmPanelMapper.insertSelective(panel);
        }
        return panel;
    }
}
