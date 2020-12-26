package top.zhouy.shoptalk.service;

/**
 * @description: 序列化
 * @author: zhouy
 * @create: 2020-12-25 11:54:00
 */
public interface Serializer {

    /**
     * 序列化算法
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转换成二进制
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成 java 对象
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
