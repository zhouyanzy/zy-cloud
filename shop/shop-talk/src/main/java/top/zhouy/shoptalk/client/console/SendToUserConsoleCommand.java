package top.zhouy.shoptalk.client.console;

import io.netty.channel.Channel;
import top.zhouy.shoptalk.bean.packet.MessageRequestPacket;

import java.util.Scanner;

/**
 * @description: 发送消息给某个某个用户
 * @author: zhouy
 * @create: 2020-12-25 10:57:00
 */
public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("发送消息给某个用户：");
        String toUserId = scanner.next();
        String message = scanner.next();
        channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
    }
}
