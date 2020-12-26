package top.zhouy.shoptalk.bean.packet;

import lombok.Data;
import lombok.NoArgsConstructor;
import top.zhouy.shoptalk.bean.Packet;

import static top.zhouy.shoptalk.bean.Constant.GROUP_MESSAGE_REQUEST;

/**
 * @description: 群信息
 * @author: zhouy
 * @create: 2020-12-25 18:07:00
 */
@Data
@NoArgsConstructor
public class GroupMessageRequestPacket extends Packet {
    private String toGroupId;
    private String message;

    public GroupMessageRequestPacket(String toGroupId, String message) {
        this.toGroupId = toGroupId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_REQUEST;
    }
}
