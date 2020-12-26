package top.zhouy.shoptalk.bean.packet;

import lombok.Data;
import top.zhouy.shoptalk.bean.Packet;

import static top.zhouy.shoptalk.bean.Constant.QUIT_GROUP_REQUEST;

/**
 * @description: 退出群聊
 * @author: zhouy
 * @create: 2020-12-25 11:50:00
 */
@Data
public class QuitGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {

        return QUIT_GROUP_REQUEST;
    }
}
