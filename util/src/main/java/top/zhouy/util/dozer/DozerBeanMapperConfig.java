package top.zhouy.util.dozer;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhouYan
 * @date 2020/3/13 15:32
 */
@Configuration
public class DozerBeanMapperConfig {

    @Bean
    public DozerBeanMapper mapper() {
        DozerBeanMapper mapper = new DozerBeanMapper();
        return mapper;
    }
}
