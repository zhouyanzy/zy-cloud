package top.zhouy.shoptalk.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import top.zhouy.shoptalk.util.PacketCodeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 解码
 * @author: zhouy
 * @create: 2020-12-25 13:51:00
 */
public class PacketDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) {
        if (in != null) {
            out = new ArrayList();
            out.add(PacketCodeUtil.decode(in));
        }
    }
}
