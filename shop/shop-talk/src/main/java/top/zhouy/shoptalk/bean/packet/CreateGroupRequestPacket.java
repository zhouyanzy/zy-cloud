package top.zhouy.shoptalk.bean.packet;

import lombok.Data;
import top.zhouy.shoptalk.bean.Packet;

import java.util.List;

import static top.zhouy.shoptalk.bean.Constant.CREATE_GROUP;

/**
 * @description: 群聊
 * @author: zhouy
 * @create: 2020-12-25 14:47:00
 */
@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP;
    }
}
