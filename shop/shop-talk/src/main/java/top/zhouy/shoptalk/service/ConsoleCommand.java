package top.zhouy.shoptalk.service;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @description: 命令执行器
 * @author: zhouy
 * @create: 2020-12-25 14:45:00
 */
public interface ConsoleCommand {

    /**
     * 执行命令
     * @param scanner
     * @param channel
     */
    void exec(Scanner scanner, Channel channel);

}
