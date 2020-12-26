package top.zhouy.shoptalk.service.impl;

import io.netty.channel.Channel;
import top.zhouy.shoptalk.service.ConsoleCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @description: 执行
 * @author: zhouy
 * @create: 2020-12-25 14:46:00
 */
public class ConsoleCommandManager implements ConsoleCommand {

    private Map<String, ConsoleCommand> consoleCommandMap;

    public ConsoleCommandManager() {
        consoleCommandMap = new HashMap<>();
        // consoleCommandMap.put("sendToUser", new SendToUserConsoleCommand());
        // consoleCommandMap.put("logout", new LogoutConsoleCommand());
        consoleCommandMap.put("joinGroup", new JoinGroupConsoleCommand());
        consoleCommandMap.put("createGroup", new CreateGroupConsoleCommand());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        //  获取第一个指令
        String command = scanner.next();

        ConsoleCommand consoleCommand = consoleCommandMap.get(command);

        if (consoleCommand != null) {
            consoleCommand.exec(scanner, channel);
        } else {
            System.err.println("无法识别[" + command + "]指令，请重新输入!");
        }
    }
}