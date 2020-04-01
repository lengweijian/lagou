package com.mybatis.core;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

/**
 * 解析XMl文件，封装成Configuration
 * 
 * @author lengweijian
 *
 */
public class XmlConfigerBuilder {

	private Configuration configuration;

	Properties properties = new Properties();


	public XmlConfigerBuilder() {
		this.configuration = new Configuration();
	}
	
	public Configuration parseConfiguration(InputStream inputStream) throws Exception {
		Document document = new SAXReader().read(inputStream);

		Element rootElement = document.getRootElement();

		doConfigurationConfig(rootElement);

		doMapperConfig(rootElement);

		populateConfiguration();

		return this.configuration;
	}

	private void populateConfiguration() {
		// 创建连接池
		ComboPooledDataSource c3p0ConnPool = new ComboPooledDataSource();
		try {
			c3p0ConnPool.setDriverClass((String)properties.get("driver"));
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		c3p0ConnPool.setJdbcUrl((String)properties.get("url"));
		c3p0ConnPool.setUser((String) properties.get("username"));
		c3p0ConnPool.setPassword((String) properties.get("password"));
		this.configuration.setDataSource(c3p0ConnPool);
	}

	private void doMapperConfig(Element rootElement) throws ClassNotFoundException {
		List<Node> mapperNodes = rootElement.selectNodes("//mapper");
		Iterator<Node> mapperIterator = mapperNodes.iterator();
		while (mapperIterator.hasNext()){
			Node node = mapperIterator.next();
			Element element = (Element)node;
			Attribute resource = element.attribute("resource");
			new XmlMapperBuilder(this.configuration).parse(Resources.getResourceStream(resource.getValue()));
		}
	}

	private void doConfigurationConfig(Element rootElement) {
		List<Node> dataSourceNodes = rootElement.selectNodes("//property");
		Iterator<Node> iterator = dataSourceNodes.iterator();

		while (iterator.hasNext()) {
			Node node = iterator.next();
			Element element = (Element)node;
			Attribute name = element.attribute("name");
			Attribute value = element.attribute("value");
			properties.put(name.getValue(),value.getValue());
		}
	}


}
