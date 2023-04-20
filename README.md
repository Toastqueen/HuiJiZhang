# 									慧记账项目
## 一.项目结构

1. Algorithm-server
   * 胶水层，基于Python构建
2. Back-end-server
   * 后端服务，基于Java及SpringBoot构建
3. Front-end-server
   * 前端服务，基于Uniapp构建

## 二.前端部署



## 三.后端部署

### 1）胶水层部分

* 确保有正确的环境依赖

  * python
  * sanic
  * pytorch
  * OpenAI key

  ```shell
  pip install sanic
  pip install pip install torch
  ```

  ```shell
  cd ./HuiJiZhang/Algorithm-server/fwwb14_37/bill_recognize/fuchuang-py-server.py
  python3 fuchuang-py-server.py
  ```

  

### **2) 后端主要服务部分**

- 确保有正确的环境依赖
  - 详情见  [环境依赖](#四.环境依赖)

1. 创建数据库`HuiJiZhang`，导入脚本`database.sql`
2. SpringBoot工程运行：(可自行打包运行)
   1. ` .\HuiJiZhang\Back-end-server\toast-user-server\src\main\java\com\toast\work\ToastUserServer`
   2. ` .\HuiJiZhang\Back-end-server\toast-fuchuang-server\src\main\java\com\toast\fuchuang\ToastFuchuangServer`
   3.   `.\HuiJiZhang\Back-end-server\gateway\src\main\java\com\toast\springcloud\GateWayMain9520`
3. 修改配置

- ToastUserServer部分

  ```yaml
  server:
    port: 端口
  # ActuatorWeb访问端口
  management:
    endpoints:
      web:
        exposure:
          include: "*"
  
    endpoint:
      health:
        show-details: always
  
  spring:
    application:
      name: toast-user-service
  
    cloud:
      nacos:
        discovery:
          server-addr: #配置Nacos地址
  
    datasource:
      type: com.alibaba.druid.pool.DruidDataSource            # 当前数据源操作类型
      driver-class-name: com.mysql.jdbc.Driver             # mysql驱动包 com.mysql.jdbc.Driver org.gjt.mm.mysql.Driver
      url: 
      username: 
      password: 
  
    redis:
      host: ip
      port: 端口
      lettuce:
        shutdown-timeout: 0ms
        pool:
          MaxTotal: 20 #最大连接数
          minIdle: 1
          maxWaitMillis: 5000
          maxIdle: 5
          testOnBorrow: true
          testOnReturn: true
          testWhileIdle: true
  
    #上传文件大小限制
    servlet:
      multipart:
        max-file-size: 100MB
        max-request-size: 100MB
  file:
    ###静态资源对外暴露的访问路径
    staticAccessPath: /api/file/**
    uploadFolder: 自定义路径
  
  mybatis:
    mapperLocations: classpath:mapper/*.xml
    type-aliases-package: com.toast.work.domain   # 所有Entity别名类所在包
  
  wxLogin:
    yiDengSecret: b7ecbb58
  ```



- ToastFuchuangServer部分

  - 特别注意常规配置最后特别配置

  - ```YAML
    toast:
      # 胶水层图形识别服务地址
      pythonServerUrl: http://127.0.0.1:8003/getPhotoJson
      # 胶水层ai服务地址
      pythonAiServerUrl: http://127.0.0.1:8003/getOpenAIJson
      # 胶水层用户画像 服务地址
      pythonUserTabServerUrl: http://47.108.202.105:8003/getUserTab
    
      api:
        url: https://openai.api2d.net/v1/chat/completions
        key: 自己的API的key
        model: 模型选择
    ```

  - 常规配置

    ```yaml
    server:
      port: 8002
    spring:
      application:
        name: toast-fuchuang-service
    
      cloud:
        nacos:
          discovery:
            server-addr:  #配置Nacos地址
    
      datasource:
        type: com.alibaba.druid.pool.DruidDataSource            # 当前数据源操作类型
        driver-class-name: com.mysql.jdbc.Driver             # mysql驱动包 com.mysql.jdbc.Driver org.gjt.mm.mysql.Driver
        url: 
    
      redis:
        host: 
        port: 
        lettuce:
          shutdown-timeout: 0ms
          pool:
            MaxTotal: 20 #最大连接数
            minIdle: 1
            maxWaitMillis: 5000
            maxIdle: 5
            testOnBorrow: true
            testOnReturn: true
            testWhileIdle: true
    
    file:
      ###静态资源对外暴露的访问路径
      staticAccessPath: /api/file/**
      ###静态资源实际存储路径  如果没有结尾的 / 就404
      uploadFolder: 自定义路径
    
    
    toast:
      # 胶水层图形识别服务地址
      pythonServerUrl: http://127.0.0.1:8003/getPhotoJson
      # 胶水层ai服务地址
      pythonAiServerUrl: http://127.0.0.1:8003/getOpenAIJson
      # 胶水层用户画像 服务地址
      pythonUserTabServerUrl: http://47.108.202.105:8003/getUserTab
    
      api:
        url: https://openai.api2d.net/v1/chat/completions
        key: 自己的API的key
        model: 模型选择
    ```

    



## 四.环境依赖

系统环境依赖表

|       环境名称       |    版本号     |                描述                |
| :------------------: | :-----------: | :--------------------------------: |
|        Uniapp        |     3.7.9     | 使用Vue.js开发跨平台应用的前端框架 |
|      JavaScript      |     1.8.5     |          前端开发语言环境          |
|        uview         |      2.0      |            app端组件库             |
|       ucharts        |     2.2.0     | 基于canvas API开发的前端应用图表库 |
|       ColorUI        |     2.1.6     |           app端css组件库           |
|         Java         |    JDK1.8     |         后端微服务开发环境         |
|        Python        |    3.9.13     |          深度学习开发环境          |
|        Torch         |    1.11.0     |            深度学习框架            |
|        Docker        |    19.3.9     |             容器化设施             |
|      Kubernetes      |    1.19.7     |          容器集群管理设施          |
|        MySQL         |      5.7      |            主存储数据库            |
|        Redis         |     6.2.5     |            辅存储数据库            |
|        Nginx         |     1.20      |             代理服务器             |
|        Nacos         |     1.2.1     |           微服务注册中心           |
|        Seata         |     1.5.2     |           分布式事务组件           |
|      SpringBoot      | 2.2.5.RELEASE |            Web开发框架             |
|        Sanic         |     21.3      |            Web开发框架             |
| Spring Cloud Gateway |     3.1.1     |           微服务网关组件           |
|    Elasticsearch     |    7.15.0     |              日志系统              |
|       Fluentd        |    1.14.3     |              日志系统              |
|        Kibana        |    7.15.0     |              日志系统              |



