package top.zhouy.commonmybatis.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.zhouy.commonmybatis.plugin.SensitivePlugin;
import top.zhouy.commonmybatis.plugin.SqlPlugin;

/**
 * @description: mybatis自定义插件
 * @author: zhouy
 * @create: 2020-12-15 13:39:00
 */
@MapperScan({"top.zhouy.*.mapper*"})
@Configuration
public class MybatisPluginConfig {

    /**
     * mybatis-plus 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }

    @Bean
    public SensitivePlugin sensitivePlugin(){
        return new SensitivePlugin();
    }

    @Bean
    public SqlPlugin sqlPlugin(){
        return new SqlPlugin();
    }
}
