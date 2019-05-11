package com.me.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Statement;
import java.util.Properties;
/**
 * @Author: qingshan
 * @Date: 2019/4/2 21:37
 * @Description: 咕泡学院，只为更好的你
 */
//拦截器顺序   excutor ==》 StatementHandler,   如果两个相同，按照配置文件中plugins的顺序执行，最后一个拦截器最先执行
//@Intercepts({ @Signature(type = StatementHandler.class, method = "query", args = { Statement.class, ResultHandler.class}) })
@Intercepts({@Signature(type = Executor.class,method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class SQLInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("当前拦截器为 SQLInterceptor");
        long startTime = System.currentTimeMillis();
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0]; // MappedStatement
        BoundSql boundSql = ms.getBoundSql(args[1]);
        /*StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();*/
        String sql = boundSql.getSql();
        System.out.println("获取到SQL语句："+sql);

        try {
            return invocation.proceed();
        }finally {
            long endTime = System.currentTimeMillis();
            System.out.println("SQL执行耗时：" + (endTime-startTime) +"ms");
        }

    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String me = properties.getProperty("me");
        // System.out.println("获取到的参数："+ gupao);
    }
}
