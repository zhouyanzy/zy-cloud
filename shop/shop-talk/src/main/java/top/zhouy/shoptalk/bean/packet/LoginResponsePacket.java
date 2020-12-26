package top.zhouy.shoptalk.bean.packet;

import lombok.Data;
import top.zhouy.shoptalk.bean.Packet;

import static top.zhouy.shoptalk.bean.Constant.LOGIN_RESPONSE;

/**
 * @description: 登录回传
 * @author: zhouy
 * @create: 2020-12-25 12:41:00
 */
@Data
public class LoginResponsePacket extends Packet {

    private String userId;

    private String userName;

    private Boolean success;

    private String msg;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
