package top.zhouy.shoptalk.bean.packet;

import lombok.Data;
import top.zhouy.shoptalk.bean.Packet;

import static top.zhouy.shoptalk.bean.Constant.JOIN_GROUP;

/**
 * @description: 加群响应
 * @author: zhouy
 * @create: 2020-12-25 18:07:00
 */
@Data
public class JoinGroupResponsePacket extends Packet {

    private Boolean success;

    private String groupId;

    private String reason;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP;
    }
}