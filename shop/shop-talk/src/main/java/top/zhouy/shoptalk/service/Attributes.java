package top.zhouy.shoptalk.service;

import io.netty.util.AttributeKey;
import top.zhouy.shoptalk.bean.Session;

/**
 * @description: attributes
 * @author: zhouy
 * @create: 2020-12-25 13:38:00
 */
public interface Attributes {

    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
