package com.weiller.demo.desktop.controller;

import com.weiller.demo.desktop.entity.CustomPanel;
import com.weiller.demo.desktop.entity.DmPanel;
import com.weiller.demo.desktop.service.DesktopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 自定义桌面
 */
@RestController
public class DesktopController {

    @Autowired
    DesktopService desktopService;

    @GetMapping("/myPanelData")
    public Object myPanelData(String userId){

        return desktopService.findCustomPanelsByUserId(userId);
    }

    @PostMapping("/deleteMyPanel")
    public void deleteMyPanel(String id){
        desktopService.deleteCustomPanel(id);
    }

    @PostMapping("/addMyPanel")
    public Object addMyPanel(CustomPanel myPanel){
       return desktopService.addCustomPanel(myPanel);
    }

    @PostMapping("/updateMyPanel")
    public void updateMyPanel(String userId,List<CustomPanel> myPanels){
        desktopService.updateCustomPanel(userId,myPanels);
    }

    @PostMapping("/savePanel")
    public void savePanel(DmPanel panel){
        desktopService.saveOrUpdatePanel(panel);
    }

    @PostMapping("/deletePanel")
    public void deletePanel(Integer id){
        desktopService.deletePanel(id);
    }

}
