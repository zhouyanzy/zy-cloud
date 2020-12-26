package top.zhouy.shoptalk.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import top.zhouy.shoptalk.bean.*;
import top.zhouy.shoptalk.bean.packet.*;
import top.zhouy.shoptalk.service.Serializer;
import top.zhouy.shoptalk.service.impl.JsonSerializer;

/**
 * @description: 封装成二进制
 * @author: zhouy
 * @create: 2020-12-25 11:57:00
 */
public class PacketCodeUtil {

    public static final int MAGIC_NUMBER = 0x12345678;

    public static ByteBuf encode(Packet packet) {
        // 1. 创建 ByteBuf 对象
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        // 2. 序列化 Java 对象
        byte[] bytes = getSerializer(packet.getSerializer()).serialize(packet);
        // 3. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(packet.getSerializer());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }

    public static ByteBuf encode(ByteBuf byteBuf, Packet packet) {
        // 1. 创建 ByteBuf 对象
        // 2. 序列化 java 对象
        byte[] bytes = getSerializer(packet.getSerializer()).serialize(packet);
        // 3. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(packet.getSerializer());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }

    public static Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);
        // 跳过版本号
        byteBuf.skipBytes(1);
        // 序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();
        // 指令
        byte command = byteBuf.readByte();
        // 数据包长度
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);
        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }
        return null;
    }

    private static Class<? extends Packet> getRequestType(byte command){
        Class<? extends Packet> clazz = null;
        switch (command) {
            case 1:
                clazz = LoginRequestPacket.class;
                break;
            case 2:
                clazz = LoginResponsePacket.class;
                break;
            case 3:
                clazz = MessageRequestPacket.class;
                break;
            case 4:
                clazz = MessageResponsePacket.class;
                break;
            case 5:
                clazz = CreateGroupRequestPacket.class;
                break;
            case 6:
                clazz = CreateGroupResponsePacket.class;
                break;
            case 7:
                clazz = ListGroupMembersRequestPacket.class;
                break;
            case 8:
                clazz = ListGroupMembersResponsePacket.class;
                break;
            case 9:
                clazz = LogoutRequestPacket.class;
                break;
            case 10:
                clazz = LogoutResponsePacket.class;
                break;
            case 11:
                clazz = QuitGroupRequestPacket.class;
                break;
            case 12:
                clazz = QuitGroupResponsePacket.class;
                break;
            case 13:
                clazz = GroupMessageRequestPacket.class;
                break;
            case 14:
                clazz = GroupMessageResponsePacket.class;
                break;
        }
        return clazz;
    }

    private static Serializer getSerializer(byte serializeAlgorithm){
        Serializer serializer = null;
        switch (serializeAlgorithm) {
            case 1:
                serializer = new JsonSerializer();
                break;
        }
        return serializer;
    }
}
