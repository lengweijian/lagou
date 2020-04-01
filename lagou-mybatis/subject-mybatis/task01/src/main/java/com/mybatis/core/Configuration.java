package com.mybatis.core;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

import javax.sql.DataSource;

/**
 * mybatis核心配置类
 * @author lengweijian
 *
 */
@Data
public class Configuration {
	
	DataSource dataSource;
	
	/**
	 * K:namespace+[.]+id
	 * V:MappedStatement
	 */
	private Map<String,MappedStatement> maps = new HashMap<String,MappedStatement>();


}
