package com.mybatis.core;

public class DefaultSqlSessionFactory implements SqlSessionFactory{

	private Configuration configuration;

	public DefaultSqlSessionFactory(Configuration configuration){
		this.configuration = configuration;
	}

	@Override
	public SqlSession openSession() {
		// TODO Auto-generated method stub
		return new DefaultSqlSession(configuration);
	}

}
