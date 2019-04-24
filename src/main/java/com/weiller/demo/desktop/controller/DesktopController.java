package com.weiller.demo.desktop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义桌面
 */
@RestController
public class DesktopController {

    @GetMapping("/panelData")
    public Object panelData(){

        return "panelData";
    }

    @PostMapping("/deletePanel")
    public Object deletePanel(){
        return "deletePanel";
    }

    @PostMapping("/savePanel")
    public Object saveOrupdate(){
        return "savePanel";
    }
}
