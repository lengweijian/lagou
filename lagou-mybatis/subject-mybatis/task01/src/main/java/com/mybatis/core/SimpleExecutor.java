package com.mybatis.core;

import com.utils.GenericTokenParser;
import com.utils.ParameterMapping;
import com.utils.ParameterMappingTokenHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author lengweijian
 * @version 1.0
 * @date 2020-03-28 13:50
 */
public class SimpleExecutor implements Executor {

    private static final Logger logger = LoggerFactory.getLogger(SimpleExecutor.class);

    PreparedStatement preparedStatement;

    ResultSet resultSet;

    Connection connection;

    @Override
    public <E> List<E> query(MappedStatement mappedStatement, Configuration configuration, Object[] params) {
        DataSource dataSource = configuration.getDataSource();
        try {
            this.connection = dataSource.getConnection();
            String sql = mappedStatement.getSql();
            // 获取传入参数类型
            Class<?> paramterType = mappedStatement.getParamterType();
            // 将#{}替换成占位符?
            BoundSql boundSql = doPlaceHolder(sql);
            String parsedSql = boundSql.getParsedSql();
            logger.info("parsedSql:" + parsedSql);

            this.preparedStatement = connection.prepareStatement(parsedSql);
            List<ParameterMapping> parameterMappingList = boundSql.getParams();

            for (int i = 0; i < parameterMappingList.size(); i++) {
                ParameterMapping parameterMapping = parameterMappingList.get(i);
                String name = parameterMapping.getContent();

                Field declaredField = paramterType.getDeclaredField(name);
                declaredField.setAccessible(true);
                for (int i1 = 0; i1 < params.length; i1++) {

                    Object obj = declaredField.get(params[0]);

                    // 给占位符赋值
                    this.preparedStatement.setObject(i + 1, obj);

                }

            }


            logger.info("执行sql:" + parsedSql);
            this.resultSet = this.preparedStatement.executeQuery();
            Class<?> resultType = mappedStatement.getResultType();

            ArrayList<E> results = new ArrayList<>();
            while (resultSet.next()) {
                ResultSetMetaData metaData = resultSet.getMetaData();

               E obj = (E)resultType.newInstance();
                for (int i = 1; i < metaData.getColumnCount(); i++) {
                    // 属性名
                    String columnName = metaData.getColumnName(i);
                    // 属性值
                    Object value = resultSet.getObject(columnName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultType);
                    //获取写方法
                    Method writeMethod = propertyDescriptor.getWriteMethod();
                    //向类中写入值
                    writeMethod.invoke(obj, value);
                }
                results.add(obj);

            }
            return results;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    private BoundSql doPlaceHolder(String sql) {
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", tokenHandler);
        String parse = genericTokenParser.parse(sql);
        List<ParameterMapping> parameterMappings = tokenHandler.getParameterMappings();
        return new BoundSql(parse, parameterMappings);

    }

    @Override
    public void close() {
        if (connection != null){
            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
