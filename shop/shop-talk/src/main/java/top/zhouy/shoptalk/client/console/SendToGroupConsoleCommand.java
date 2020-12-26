package top.zhouy.shoptalk.client.console;

import io.netty.channel.Channel;
import top.zhouy.shoptalk.bean.packet.GroupMessageRequestPacket;

import java.util.Scanner;

/**
 * @description: 发送消息给某个某个群组
 * @author: zhouy
 * @create: 2020-12-25 10:57:00
 */
public class SendToGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("发送消息给某个某个群组：");

        String toGroupId = scanner.next();
        String message = scanner.next();
        channel.writeAndFlush(new GroupMessageRequestPacket(toGroupId, message));

    }
}
