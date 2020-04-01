package com.mybatis.core;

import java.util.List;

/**
 * TODO
 *
 * @author lengweijian
 * @version 1.0
 * @date 2020-03-28 13:50
 */
public interface Executor {
    <E> List<E> query(MappedStatement mappedStatement, Configuration configuration, Object[] params);

    void close();
}
