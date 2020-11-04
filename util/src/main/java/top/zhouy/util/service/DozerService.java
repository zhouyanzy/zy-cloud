package top.zhouy.util.service;

/**
 * 对象复制接口
 * @author zhouYan
 * @date 2020/3/13 15:34
 */
public interface DozerService {

    /**
     * 转换对象
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    <T> T convert(Object source, Class targetClass);
}
