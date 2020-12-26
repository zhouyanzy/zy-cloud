package top.zhouy.shoptalk.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.stereotype.Component;
import top.zhouy.shoptalk.handler.SplitDecoder;
import top.zhouy.shoptalk.handler.PacketCodecHandler;
import top.zhouy.shoptalk.server.handler.AuthHandler;
import top.zhouy.shoptalk.server.handler.ImHandler;
import top.zhouy.shoptalk.server.handler.LoginRequestHandler;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;

/**
 * @description: Netty服务端
 * @author: zhouy
 * @create: 2020-12-25 10:57:00
 */
@Component
public class NettyServer {

    private int PORT = 8000;

    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup work = new NioEventLoopGroup();

    @PostConstruct
    public void start() throws Exception {
        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boss, work)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new SplitDecoder());
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        ch.pipeline().addLast(AuthHandler.INSTANCE);
                        ch.pipeline().addLast(ImHandler.INSTANCE);
                    }
                });

        bind(serverBootstrap, PORT);
    }

    @PreDestroy
    private void destroy() throws Exception{
        boss.shutdownGracefully();
        work.shutdownGracefully();
        System.out.println("关闭server");
    }

    private void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
            }
        });
    }
}
