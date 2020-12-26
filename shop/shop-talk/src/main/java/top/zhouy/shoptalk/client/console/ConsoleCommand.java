package top.zhouy.shoptalk.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @description: 客户端命令输入
 * @author: zhouy
 * @create: 2020-12-25 10:57:00
 */
public interface ConsoleCommand {

    /**
     * 执行cmd命令
     * @param scanner
     * @param channel
     */
    void exec(Scanner scanner, Channel channel);
}
