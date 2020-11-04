package top.zhouy.util.service.impl;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zhouy.util.service.DozerService;

/**
 * 对象复制接口实现类
 * @author zhouYan
 * @date 2020/3/13 15:34
 */
@Service
public class DozerServiceImpl implements DozerService {

    @Autowired
    private Mapper dozerMapper;

    @Override
    public <T> T convert(Object source, Class targetClass) {
        return (T) dozerMapper.map(source, targetClass);
    }
}
