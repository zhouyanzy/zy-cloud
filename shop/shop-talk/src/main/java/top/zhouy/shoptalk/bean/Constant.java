package top.zhouy.shoptalk.bean;

/**
 * @description: 常量
 * @author: zhouy
 * @create: 2020-12-25 12:21:00
 */
public class Constant {

    /**
     * command，登录
     */
    public static final byte LOGIN_REQUEST = 1;

    /**
     * command，登录回传
     */
    public static final byte LOGIN_RESPONSE = 2;

    /**
     * command，发送消息
     */
    public static final byte MESSAGE_REQUEST = 3;

    /**
     * command，响应消息
     */
    public static final byte MESSAGE_RESPONSE = 4;

    /**
     * command，创建群聊
     */
    public static final byte CREATE_GROUP = 5;

    /**
     * command，加入群聊
     */
    public static final byte JOIN_GROUP = 6;

    /**
     * command，群聊成员
     */
    public static final byte LIST_GROUP_MEMBERS_REQUEST = 7;

    /**
     * command，群聊成员
     */
    public static final byte LIST_GROUP_MEMBERS_RESPONSE = 8;

    /**
     * command，退出登录
     */
    public static final byte LOGOUT_REQUEST = 9;

    /**
     * command，退出登录
     */
    public static final byte LOGOUT_RESPONSE = 10;

    /**
     * command，退出群聊
     */
    public static final byte QUIT_GROUP_REQUEST = 11;

    /**
     * command，退出群聊
     */
    public static final byte QUIT_GROUP_RESPONSE = 12;

    /**
     * command，群消息
     */
    public static final byte GROUP_MESSAGE_REQUEST = 13;

    /**
     * command，群消息
     */
    public static final byte GROUP_MESSAGE_RESPONSE = 14;

    /**
     * serializer，json
     */
    public static final byte SERIALIZER_JSON = 1;
}
