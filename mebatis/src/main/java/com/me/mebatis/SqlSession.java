package com.me.mebatis;


public class SqlSession {
    private MyConfiguration configuration;
    private MyExecutor executor;

    public SqlSession(MyConfiguration configuration, MyExecutor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    public <T>T selectOne(String statementId, Object paramter){
        String sql = configuration.sqlMappings.getString(statementId);
        return executor.query(sql,paramter);
    }
    public <T>T getMapper(Class clazz){
        return configuration.getMapper(clazz,this);
    }
}
