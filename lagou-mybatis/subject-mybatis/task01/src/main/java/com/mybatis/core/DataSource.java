package com.mybatis.core;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 数据源
 * @author lengweijian
 *
 */
@Data
@AllArgsConstructor
public class DataSource {
	private String driver;
	private String url;
	private String username;
	private String password;

}
