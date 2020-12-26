package top.zhouy.shoptalk.service.impl;

import io.netty.channel.Channel;
import top.zhouy.shoptalk.bean.packet.JoinGroupRequestPacket;
import top.zhouy.shoptalk.service.ConsoleCommand;

import java.util.Scanner;

/**
 * @description: 加群
 * @author: zhouy
 * @create: 2020-12-25 18:07:00
 */
public class JoinGroupConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        JoinGroupRequestPacket joinGroupRequestPacket = new JoinGroupRequestPacket();

        System.out.print("输入 groupId，加入群聊：");
        String groupId = scanner.next();

        joinGroupRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(joinGroupRequestPacket);
    }
}
