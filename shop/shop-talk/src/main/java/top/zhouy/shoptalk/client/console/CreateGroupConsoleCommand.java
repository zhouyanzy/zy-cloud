package top.zhouy.shoptalk.client.console;

import io.netty.channel.Channel;
import top.zhouy.shoptalk.bean.packet.CreateGroupRequestPacket;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @description: 创建群聊
 * @author: zhouy
 * @create: 2020-12-25 10:57:00
 */
public class CreateGroupConsoleCommand implements ConsoleCommand {

    private static final String USER_ID_SPLIT = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();
        System.out.print("【拉人群聊】输入 userId 列表，userId 之间英文逗号隔开：");
        String userIds = scanner.next();
        createGroupRequestPacket.setUserIdList(Arrays.asList(userIds.split(USER_ID_SPLIT)));
        channel.writeAndFlush(createGroupRequestPacket);
    }

}
