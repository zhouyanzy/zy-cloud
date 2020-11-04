package top.zhouy.blogmanage.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;


/**
 * mybatisPlus自动生成配置
 * @author zhouYan
 * @date 2020/3/13 10:47
 */
public class MpGenerator {

    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        AutoGenerator autoGenerator = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        // 得到当前项目的路径
        String oPath = System.getProperty("user.dir");
        // 生成文件输出根目录
        gc.setOutputDir(oPath + "/blog/blog-manage/src/main/java");
        // 生成完成后不弹出文件框
        gc.setOpen(false);
        // 文件覆盖
        gc.setFileOverride(true);
        // 不需要ActiveRecord特性的请改为false
        gc.setActiveRecord(false);
        // XML二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columnList
        gc.setBaseColumnList(false);
        // 作者
        gc.setAuthor("zhouy");

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        autoGenerator.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        // 设置数据库类型
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("zy");
        dsc.setPassword("123456");
        // 指定数据库
        dsc.setUrl("jdbc:mysql://47.101.151.43:3306/blog?useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&serverTimezone=GMT%2B8");
        autoGenerator.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 需要生成的表
        strategy.setInclude(new String[] { "blog_article", "blog_article_category", "blog_category" });
        // 设置前缀，strategy.setTablePrefix(new String[] { "blog" })
        strategy.setSuperServiceClass(null);
        strategy.setSuperServiceImplClass(null);
        strategy.setSuperMapperClass(null);
        autoGenerator.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("top.zhouy.blogmanage");
        pc.setController("controller");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("mapper");
        pc.setEntity("bean.entity");
        pc.setXml("xml");
        autoGenerator.setPackageInfo(pc);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        // 不需要其他的类型时，直接设置为null就不会成对应的模版了
        // templateConfig.setEntity("...");
        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也
        // 可以自定义模板名称 只要放到目录下，名字不变 就会采用这个模版 下面这句有没有无所谓
        // 模版去github上看地址：
        /**
         * https://github.com/baomidou/mybatis-plus/tree/3.0/mybatis-plus-generator/src/main/resources/templates
         */
        // templateConfig.setEntity("/templates/entity.java");
        autoGenerator.setTemplate(templateConfig);

        // 执行生成
        autoGenerator.execute();
    }

}
