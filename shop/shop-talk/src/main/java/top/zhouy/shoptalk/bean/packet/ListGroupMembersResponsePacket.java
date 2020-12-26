package top.zhouy.shoptalk.bean.packet;

import lombok.Data;
import top.zhouy.shoptalk.bean.Packet;
import top.zhouy.shoptalk.bean.Session;

import java.util.List;

import static top.zhouy.shoptalk.bean.Constant.LIST_GROUP_MEMBERS_RESPONSE;

/**
 * @description: 群聊成员
 * @author: zhouy
 * @create: 2020-12-25 18:07:00
 */
@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {

        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
