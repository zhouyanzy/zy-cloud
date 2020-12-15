package top.zhouy.commonmybatis.plugin;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import top.zhouy.commonmybatis.config.SpringContextUtil;
import top.zhouy.commonmybatis.mapper.MyCommonMapper;

import java.sql.SQLException;
import java.util.Properties;

import static top.zhouy.commonauthclient.entity.UserUtils.LOCAL_USER;

/**
 * @description: 重写Sql
 * @author: zhouy
 * @create: 2020-12-15 13:42:00
 */
@Slf4j
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class SqlPlugin implements Interceptor {

    private static MyCommonMapper myCommonMapper;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (myCommonMapper == null) {
            myCommonMapper = SpringContextUtil.getBean("myCommonMapper");
        }
        final Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        // 判断sql类型
        SqlCommandType commandType = ms.getSqlCommandType();
        if (! (SqlCommandType.INSERT.equals(commandType) || SqlCommandType.UPDATE.equals(commandType) || SqlCommandType.DELETE.equals(commandType))) {
            return invocation.proceed();
        }
        // 获取sql
        Object parameterObject = args[1];
        BoundSql boundSql = ms.getBoundSql(parameterObject);
        String sql = boundSql.getSql();
        if (StringUtils.isBlank(sql)) {
            return invocation.proceed();
        }
        if (sql.indexOf("UPDATE") != -1 && sql.indexOf("WHERE") != -1) {
            if (sql.indexOf("updater_id") == -1) {
                String[] strings = sql.split("WHERE");
                StringBuilder stringBuilders = new StringBuilder();
                stringBuilders.append(strings[0]);
                String table = strings[0].split("SET")[0].split("UPDATE")[1];
                if (StringUtils.isNotBlank(table)) {
                    table = table.trim();
                    Boolean canAdd = false;
                    if (StringUtils.isNotBlank(myCommonMapper.hadColumn(table, "updater_id", null))) {
                        canAdd = true;
                    } else {
                        try {
                            myCommonMapper.addColumn(table, "updater_id", "BIGINT(20)", "更新人");
                            canAdd = true;
                        } catch (Exception e) {
                            log.error("表{}增加字段失败{}", table, e.getMessage());
                        }
                    }
                    if (canAdd) {
                        Long userId = LOCAL_USER.get() != null ? LOCAL_USER.get().getId() : 0L;
                        stringBuilders.append(" , updater_id = ").append(userId);
                        stringBuilders.append(" WHERE ").append(strings[1]);
                        // 包装sql后，重置到invocation中
                        resetSql2Invocation(invocation, String.valueOf(stringBuilders));
                    }
                }
            }
        }

        // 返回，继续执行
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object obj) {
        return Plugin.wrap(obj, this);
    }

    @Override
    public void setProperties(Properties arg0) {
    }

    /**
     * 包装sql后，重置到invocation中
     * @param invocation
     * @param sql
     * @throws SQLException
     */
    private void resetSql2Invocation(Invocation invocation, String sql) throws SQLException {
        final Object[] args = invocation.getArgs();
        MappedStatement statement = (MappedStatement) args[0];
        Object parameterObject = args[1];
        BoundSql boundSql = statement.getBoundSql(parameterObject);
        MappedStatement newStatement = newMappedStatement(statement, new BoundSqlSqlSource(boundSql));
        MetaObject msObject =  MetaObject.forObject(newStatement, new DefaultObjectFactory(), new DefaultObjectWrapperFactory(),new DefaultReflectorFactory());
        msObject.setValue("sqlSource.boundSql.sql", sql);
        args[0] = newStatement;
    }

    private MappedStatement newMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder =
                new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuilder keyProperties = new StringBuilder();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

    /**
     * 定义一个内部辅助类，作用是包装sql
     */
    class BoundSqlSqlSource implements SqlSource {

        private BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
}
