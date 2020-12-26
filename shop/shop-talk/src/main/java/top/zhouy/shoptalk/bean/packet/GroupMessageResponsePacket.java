package top.zhouy.shoptalk.bean.packet;

import lombok.Data;
import top.zhouy.shoptalk.bean.Packet;
import top.zhouy.shoptalk.bean.Session;

import static top.zhouy.shoptalk.bean.Constant.GROUP_MESSAGE_RESPONSE;

/**
 * @description: 群信息
 * @author: zhouy
 * @create: 2020-12-25 18:07:00
 */
@Data
public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte getCommand() {

        return GROUP_MESSAGE_RESPONSE;
    }
}
