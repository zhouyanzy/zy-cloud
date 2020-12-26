package top.zhouy.shoptalk.client.console;

import io.netty.channel.Channel;
import top.zhouy.shoptalk.bean.packet.LoginRequestPacket;

import java.util.Scanner;

/**
 * @description: 登录
 * @author: zhouy
 * @create: 2020-12-25 10:57:00
 */
public class LoginConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        System.out.print("输入用户名登录: ");
        loginRequestPacket.setUserName(scanner.nextLine());
        loginRequestPacket.setPassword("pwd");

        // 发送登录数据包
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
