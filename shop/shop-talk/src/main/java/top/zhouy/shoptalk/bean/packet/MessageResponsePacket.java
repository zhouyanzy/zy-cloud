package top.zhouy.shoptalk.bean.packet;

import lombok.Data;
import top.zhouy.shoptalk.bean.Packet;

import static top.zhouy.shoptalk.bean.Constant.MESSAGE_RESPONSE;

/**
 * @description: 响应消息
 * @author: zhouy
 * @create: 2020-12-25 13:36:00
 */
@Data
public class MessageResponsePacket extends Packet {

    private String message;

    private String fromUserId;

    private String fromUserName;

    @Override
    public Byte getCommand() {

        return MESSAGE_RESPONSE;
    }
}
