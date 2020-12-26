package top.zhouy.shoptalk.bean;

import lombok.Data;

import static top.zhouy.shoptalk.bean.Constant.SERIALIZER_JSON;

/**
 * @description: 包
 * @author: zhouy
 * @create: 2020-12-25 11:50:00
 */
@Data
public abstract class Packet {

    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     * 指令
     * @return
     */
    public abstract Byte getCommand();

    /**
     * 序列化方式
     * @return
     */
    public Byte getSerializer(){
        return SERIALIZER_JSON;
    }
}
