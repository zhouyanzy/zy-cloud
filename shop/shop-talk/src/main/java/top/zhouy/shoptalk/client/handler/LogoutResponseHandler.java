package top.zhouy.shoptalk.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import top.zhouy.shoptalk.bean.packet.LogoutResponsePacket;
import top.zhouy.shoptalk.util.SessionUtil;

/**
 * @description: 退出登录
 * @author: zhouy
 * @create: 2020-12-25 10:57:00
 */
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket logoutResponsePacket) {
        SessionUtil.unBindSession(ctx.channel());
    }
}
