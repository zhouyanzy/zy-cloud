package top.zhouy.shoptalk.client.console;

import io.netty.channel.Channel;
import top.zhouy.shoptalk.bean.packet.ListGroupMembersRequestPacket;

import java.util.Scanner;

/**
 * @description: 群聊列表
 * @author: zhouy
 * @create: 2020-12-25 10:57:00
 */
public class ListGroupMembersConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        ListGroupMembersRequestPacket listGroupMembersRequestPacket = new ListGroupMembersRequestPacket();
        System.out.print("输入 groupId，获取群成员列表：");
        String groupId = scanner.next();
        listGroupMembersRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(listGroupMembersRequestPacket);
    }
}
