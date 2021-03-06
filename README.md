```
/**
 * @author zhouy
 * @date 2020/03/09
 */
```
# zy-微服务后端项目

## 项目目录结构说明
```
├─zy
│  ├─basic--------------------------------------微服务基础模块
│  │  │─basic-auth--------------------------------授权中心
│  │  │─basic-config------------------------------× 配置中心config，已改成nacos
│  │  │─basic-eureka------------------------------× 注册中心eureka，已改成nacos
│  │  │─basic-gateway-----------------------------网关gateway
│  │  │─basic-zuul--------------------------------× 网关zuul，已改成gateway
│  │  │─basic-hystrix-dashboard-------------------hystrix监控
│  │  │─basic-xxl-executor------------------------xxl-job执行器
│  │  │─basic-tm----------------------------------× Tx-Manager，已改成seata
│  │  └─basic-admin-------------------------------spring-boot admin
│  ├─blog---------------------------------------博客业务模块
│  │  │─blog-manager------------------------------博客后台管理
│  │  └─blog-home---------------------------------博客首页
│  ├─common-------------------------------------共通业务模块
│  ├─shop---------------------------------------商场业务模块
│  │  │─shop-product------------------------------商品
│  │  │─shop-home---------------------------------首页
│  │  │─shop-order--------------------------------订单
│  │  │─shop-pay----------------------------------支付
│  │  └─shop-talk---------------------------------社交
│  ├─util---------------------------------------工具模块
```

## 可以直接从官网下载后启动的服务
 - nacos
 - sentinel
 - seata

## 系统端口
 - `8770` basic-gateway
 - `8848` basic-nacos
 - `8760` × basic-eureka
 - `8088` × basic-zuul
 - `8780` × basic-config
 - `8790` basic-auth
 - `8800` basic-hystrix-dashboard
 - `8810` blog-manage
 - `8820` blog-home
 - `8910` shop-product
 - `8920` shop-home
 - `8930` shop-order
 - `8940` shop-pay
 - `8950` shop-talk
 - `9010` × tm
 - `9020` × tm-listen
 - `9030` xxl-job-admin
 - `9040` xxl-job-executor
 - `9050` basic-admin
 - `9100` basic-sentinel-dashboard
 - `8901` basic-seata

## 项目地址
 - × eureka：http://42.192.93.125:8760
 - hystrix监控：http://42.192.93.125:8800/hystrix
 - swagger文档：http://www.zhouy.top:8770/doc.html
 - × lcn后台管理：http://42.192.93.125:9010/admin/index.html#/login，密码：codingapi
 - xxl后台管理：http://42.192.93.125:9030/xxl-job-admin，账号密码：admin/123456
 - spring-boot admin监控：http://42.192.93.125:9050/login，账号密码：admin/123456
 - nacos: http://81.69.162.162:8848/nacos，账号密码：nacos/nacos
 - sentinel-dashboard: http://81.69.162.162:9100，账号密码：sentinel/sentinel
 - seata: http://81.69.162.162:8091
 - zipkin: http://81.69.162.162:9411

## Host配置
 - 127.0.0.1 www.zhouy.top

## 参考项目
 - × 分布式事务框架tx-lcn
 - 分布式任务调度xxl-job