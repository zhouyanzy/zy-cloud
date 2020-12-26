package top.zhouy.shoptalk.bean.packet;

import lombok.Data;
import top.zhouy.shoptalk.bean.Packet;

import java.util.List;

import static top.zhouy.shoptalk.bean.Constant.CREATE_GROUP;

/**
 * @description: 群聊响应
 * @author: zhouy
 * @create: 2020-12-25 18:04:00
 */
@Data
public class CreateGroupResponsePacket extends Packet {

    private Boolean success;

    private String groupId;

    private List<String> userNameList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP;
    }
}