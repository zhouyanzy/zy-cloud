package top.zhouy.shoptalk.bean.packet;

import lombok.Data;
import lombok.NoArgsConstructor;
import top.zhouy.shoptalk.bean.Packet;

import static top.zhouy.shoptalk.bean.Constant.MESSAGE_REQUEST;

/**
 * @description: 消息
 * @author: zhouy
 * @create: 2020-12-25 13:35:00
 */
@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {

    private String toUserId;

    private String message;

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }

}
