package com.mybatis.core;

import java.lang.reflect.*;
import java.util.ArrayList;
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
        return executor.query(configuration.getMaps().get(statement),configuration,params);
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

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        return (T)Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{mapperClass}, (proxy, method, args) -> {
            // selectOne
            String targetMethodName = method.getName();
            if (targetMethodName.contains(MethodType.INSERT.getAction())){

            }else if (targetMethodName.contains(MethodType.DELETE.getAction())){

            }else if (targetMethodName.contains(MethodType.UPDATE.getAction())){

            }else{

            }
            // className:namespace
            String className = method.getDeclaringClass().getName();
            // statementId
            String key = className+"."+targetMethodName;

            MappedStatement mappedStatement = configuration.getMaps().get(key);

            Type genericReturnType = method.getGenericReturnType();

            ArrayList arrayList = new ArrayList();
            if (genericReturnType instanceof ParameterizedType){
                return selectList(key,args);
            }
            return selectOne(key,args);
        });
    }

    @Override
    public <T> void insert(T t) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public <T> void update(Integer id, T t) {

    }
}
