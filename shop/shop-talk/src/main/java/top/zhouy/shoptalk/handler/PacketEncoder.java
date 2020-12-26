package top.zhouy.shoptalk.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import top.zhouy.shoptalk.bean.Packet;
import top.zhouy.shoptalk.util.PacketCodeUtil;

/**
 * @description: 编码
 * @author: zhouy
 * @create: 2020-12-25 13:54:00
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) {
        PacketCodeUtil.encode(out, packet);
    }
}