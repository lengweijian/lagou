package com.mybatis.core;

import lombok.Data;

@Data
public class MappedStatement {
		
	private String id;
	
	private String sql;
	/**
	 * 输入参数
	 */
	private Class<?> paramterType;
	/**
	 * 输出参数
	 */
	private Class<?> resultType;
	
}
