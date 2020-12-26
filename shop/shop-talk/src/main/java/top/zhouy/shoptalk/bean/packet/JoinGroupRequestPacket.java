package top.zhouy.shoptalk.bean.packet;

import lombok.Data;
import top.zhouy.shoptalk.bean.Packet;

import static top.zhouy.shoptalk.bean.Constant.JOIN_GROUP;

/**
 * @description: 加群
 * @author: zhouy
 * @create: 2020-12-25 18:07:00
 */
@Data
public class JoinGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP;
    }
}