# zy-微服务后端项目

## 项目目录结构说明

```
├─zy
│  ├─basic--------------------------------------微服务基础模块
│  │  │─basic-auth--------------------------------授权中心
│  │  │─basic-config------------------------------配置中心config
│  │  │─basic-eureka------------------------------注册中心eureka
│  │  │─basic-zuul--------------------------------网关zuul
│  │  │─basic-hystrix-dashboard-------------------hystrix监控
│  │  └─basic-tm----------------------------------Tx-Manager
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
## 系统端口
 - `8760` basic-eureka
 - `8770` basic-zuul
 - `8780` basic-config
 - `8790` basic-auth
 - `8800` basic-hystrix-dashboard
 - `8810` blog-manage
 - `8820` blog-home
 - `8910` shop-product
 - `8920` shop-home
 - `8930` shop-order
 - `8940` shop-pay
 - `8950` shop-talk
 - `9010` tm
 - `9020` tm-listen

## 项目地址
 - 注册中心：http://zhouy.top:8760
 - hystrix监控：http://zhouy.top:8800/hystrix
 - swagger文档：http://zhouy.top:8770/swagger-ui.html
 - lcn后台管理：http://zhouy.top:9010/admin/index.html#/login，密码：codingapi

## Host配置
 - 127.0.0.1 zhouy.top

## 参考项目
 - blog-manage部分参考了dbblog
 - 分布式框架tx-lcn
```
/**
 * @author zhouy
 * @date 2020/03/09
 */
```