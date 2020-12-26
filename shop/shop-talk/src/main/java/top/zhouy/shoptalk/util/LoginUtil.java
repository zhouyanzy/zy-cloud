package top.zhouy.shoptalk.util;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import top.zhouy.shoptalk.service.Attributes;

/**
 * @description: 登录
 * @author: zhouy
 * @create: 2020-12-25 13:37:00
 */
public class LoginUtil {

    /**
     * 标记为登录
     * @param channel
     */
    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    /**
     * 判断是否已经登录
     * @param channel
     * @return
     */
    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        return loginAttr.get() != null;
    }
}
