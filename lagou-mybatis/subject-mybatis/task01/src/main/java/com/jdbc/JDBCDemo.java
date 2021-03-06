package com.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * JDBC存在问题：1.频繁的与数据库建连、断连非常浪费资源，从而影响系统性能。
 * 2.SQL属于硬编码在Java代码中的，没有跟Java源代码分离，会导致SQL改动需要从新编译Java代码。
 * 3.配置文件（数据库连接信息）没有做到定制化适配任何一种数据库。                 
 * PreparedStatement对SQL的占位符赋值的时候，存在硬编码。
 * 4.对结果集的处理，需要手动的将每一个列结果赋值到某一个DO对象上，这样会导致每次修改SQL都会增减Java赋值代码。
 * 
 * 解决：1.数据库频繁建连断连：使用数据库连接池。
 * 2.SQL语句以及参数占位符的硬编码：使用配置文件
 * 3.手动封装返回结果集：反射、内省
 * @author lengweijian
 *
 */
public class JDBCDemo {
	
	private static Connection conn;
	private static PreparedStatement ps;
	private static ResultSet resultSet;
	
	static {
		
		InputStream inputStream = JDBCDemo.class.getClassLoader().getResourceAsStream("properties/jdbc.properties");
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			System.out.println("IO异常。。");
			e.printStackTrace();
		}
		//java spi机制
		//String driver = (String) properties.get("jdbc.driver");
		String url = (String) properties.get("jdbc.url");
		String username = (String) properties.get("username");
		String password = (String) properties.get("password");
//		try {
//			Class.forName(driver);
//		} catch (ClassNotFoundException e) {
//			System.out.println("类没找到。。。");
//			e.printStackTrace();
//		}
		
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		try {
			query();
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void close() throws SQLException {
		if (resultSet!=null) {
			resultSet.close();
		}
		if (ps!=null) {
			ps.close();
		}
		if (conn!=null) {
			conn.close();
		}
	
		
	}

	/**
	 * 查询mysql
	 * @throws SQLException 
	 */
	private static void query() throws SQLException {
		
		ps = conn.prepareStatement("SELECT username,password,birthday FROM user WHERE id = ?");
		ps.setInt(1, 1);
		resultSet = ps.executeQuery();
		while (resultSet.next()) {
			String username = resultSet.getString("username");
			String password = resultSet.getString("password");
			String birth = resultSet.getString("birthday");
			System.out.printf("username:%s\tpassword:%s\tbirth:%s\t\n",username,password,birth);
		}
	}

}
