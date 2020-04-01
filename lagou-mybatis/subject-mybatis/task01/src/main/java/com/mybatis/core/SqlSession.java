package com.mybatis.core;

import java.util.List;

/**
 * CURD方法
 * @author lengweijian
 *
 */
public interface SqlSession {

	/**
	 * 查询全部
	 * @param statement
	 * @param params
	 * @param <E>
	 * @return
	 */
	<E> List<E> selectList(String statement,Object...params);

	/**
	 * 查询某一条记录
	 * @param statement
	 * @param params
	 * @param <T>
	 * @return
	 */
	<T> T selectOne(String statement,Object...params);

	/**
	 * 释放资源
	 */
	void close();

	<T> T getMapper(Class<?> mapperClass);

}
