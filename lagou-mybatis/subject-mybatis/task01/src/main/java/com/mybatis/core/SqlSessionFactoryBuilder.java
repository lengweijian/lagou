package com.mybatis.core;

import java.io.InputStream;

public class SqlSessionFactoryBuilder {
	
	private Configuration configuration;
	
	public SqlSessionFactoryBuilder() {
		this.configuration = new Configuration();
	}
	
	/**
	 * 1.解析xml文件，封装到Configuration，MappedStatement
	 * 2.
	 * @return
	 */
	public static SqlSessionFactory build(InputStream inputStream) throws Exception {
		// dom4j解析配置文件，将解析的内容封装到Configuration和MappedStatement中。
		XmlConfigerBuilder xmlConfigerBuilder = new XmlConfigerBuilder();
		Configuration configuration = xmlConfigerBuilder.parseConfiguration(inputStream);
		// 创建SqlSessionFactory的默认实现类DefaultSqlSession
		return new DefaultSqlSessionFactory(configuration);
	}
}
