# mydemo
日常学习工作的一些案例,包括自定义桌面、离线地图等。
## 代码结构

    - java  
        - desktop
        - 
    
    - resource
        - static
            - desktop #自定义桌面
            - map    #地图
            

## 案例

###1. 自定义桌面
自定义桌面，是由前端可拖拽/拉伸的div案例改造而成，实现页面内容组件化，每个页面子模块都可以自定义。
模块展示的位置/大小均可以自定义。模块内容可以是html片段、url(不支持跨域)等。

json格式参数配置展示如下：
#### 案例1 （页面中图内容由echarts实现）
访问地址 ：http://localhost:8081/desktop/demo.html
```json
[
    {"id":101,"x": 2, "y": 2, "width": 4, "height": 2,"src":"/test/page-1.html"},
    {"id":102,"x": 2, "y": 0, "width": 4, "height": 2,"src":"/test/page-2.html"},
    {"id":103,"x": 6, "y": 0, "width": 6, "height": 4,"src":"/test/page-3.html"},
    {"id":104,"x": 0, "y": 0, "width": 2, "height": 4,"src":"/test/page-4.html"}
] 
  
```
![案例截图](./src/main/resources/pic/demo_desktop_01.png '自定义桌面1截图')      
           
#### 案例2  （仿windows10样式）
访问地址 ：http://localhost:8081/desktop/demo2.html
```json
[
    {"id":101,"title":"办税日历","x": 0, "y": 0, "width": 2, "height": 2,"src":"<div style='background-color: #9665a6;height: 100%;display: flex;'><i class='iconfont' style='margin: auto;'>&#xe91e;</i></div>"},
    {"id":102,"title":"资料","x": 2, "y": 1, "width": 1, "height": 1,"src":"<div style='background-color: #5072cc;height: 100%;display: flex;'><i class='iconfont' style='margin: auto;'>&#xe91c;</i></div>"},
    {"id":103,"title":"银税互动","x": 3, "y": 0, "width": 4, "height": 2,"src":"<div style='background-color: #4458ab;height: 100%;display: flex;'><i class='iconfont' style='margin: auto;'>&#xe911;</i></div>"},
    {"id":104,"title":"下载专区","x": 2, "y": 0, "width": 1, "height": 1,"src":"<div style='background-color: #6e90c1;height: 100%;display: flex;'><i class='iconfont' style='margin: auto;'>&#xe904;</i></div>"}
] 
```
![案例截图](./src/main/resources/pic/demo_desktop_02.png '自定义桌面2截图')      
           
###2. 离线地图
案例中使用百度地图作为离线地图,简单步骤如下：
1. 百度开发api的js文件下载至本地.
2. 修改api js将外网访问部分改为本地，如加载api模块js等.
3. 下载百度地图瓦片(即各层级需要组合的图片)
4. 开始使用百度地图api开发
#### 案例展示
1. 覆盖物点/线： http://localhost:9090/map/demo1.html
![案例截图](./src/main/resources/pic/demo_map_01.png '离线百度地图-点线覆盖')      

2. 热力图： http://localhost:9090/map/demo2.html    
![案例截图](./src/main/resources/pic/demo_map_02.png '离线百度地图-热力图')      

###3.websoket模仿在线聊天
访问地址：http://localhost:9090/websoket/index.html
![案例截图](./src/main/resources/pic/websocket1.png 'websoket模仿在线聊天')   
