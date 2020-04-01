package com.mybatis.core;

import java.util.List;

/**
 * TODO
 *
 * @author lengweijian
 * @version 1.0
 * @date 2020-03-28 13:47
 */
public class DefaultSqlSession implements SqlSession{

    private final Configuration configuration;

    private Executor executor = new SimpleExecutor();

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statement, Object... params) {

        MappedStatement mappedStatement = configuration.getMaps().get(statement);

        return executor.query(mappedStatement,configuration,params);
    }

    @Override
    public <T> T selectOne(String statement, Object... params) {

        if (selectList(statement,params).size() == 1){
            return (T) selectList(statement,params).get(0);
        }else {
            return (T) new RuntimeException("查询结果过多");
        }
    }

    @Override
    public void close() {
        executor.close();
    }
}
