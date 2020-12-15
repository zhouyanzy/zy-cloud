package top.zhouy.commonmybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * @description: 公共mapper，查询公共信息
 * @author: zhouy
 * @create: 2020-12-15 17:07:00
 */
@Component
public interface MyCommonMapper {

    /**
     * 增加列
     * @param table
     * @param columnName
     * @param columnType
     * @param comment
     * @return
     */
    @Update("ALTER TABLE ${table} ADD  ${columnName} ${columnType} DEFAULT NULL COMMENT #{comment}")
    int addColumn(@Param("table") String table, @Param("columnName") String columnName, @Param("columnType") String columnType, @Param("comment") String comment);

    /**
     * 判断是否有表
     * @param table
     * @param columnName
     * @param tableSchema
     * @return
     */
    @Select("SELECT column_name FROM information_schema.columns WHERE\n" +
            "        table_name = #{table} AND column_name= #{columnName}\n" +
            "        LIMIT 1")
    String hadColumn(@Param("table") String table, @Param("columnName") String columnName, @Param("tableSchema") String tableSchema);
}
