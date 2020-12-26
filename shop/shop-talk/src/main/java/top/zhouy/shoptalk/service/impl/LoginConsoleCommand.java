package top.zhouy.shoptalk.service.impl;

import io.netty.channel.Channel;
import top.zhouy.shoptalk.bean.packet.LoginRequestPacket;
import top.zhouy.shoptalk.service.ConsoleCommand;

import java.util.Scanner;

/**
 * @description: 登录
 * @author: zhouy
 * @create: 2020-12-25 18:00:00
 */
public class LoginConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        System.out.print("请输入用户id");
        String userId = scanner.next();
        loginRequestPacket.setUserId(userId);
        channel.writeAndFlush(loginRequestPacket);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {

        }
    }
}

