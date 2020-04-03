package com.mybatis.core;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * 解析mapper.xml文件
 */
public class XmlMapperBuilder {

    private Configuration configuration;

    public XmlMapperBuilder(Configuration configuration){
        this.configuration = configuration;
    }

    public void parse(InputStream inputStream) throws ClassNotFoundException {
        Document document = null;
        try {
            document = new SAXReader().read(inputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        Element rootElement = document.getRootElement();

        String namespace = rootElement.attribute("namespace").getValue();

        List<Node> nodes = rootElement.selectNodes("//select");

        for (Node node : nodes) {
            MappedStatement mappedStatement = new MappedStatement();
            Element element = (Element) node;
            String id = element.attributeValue("id");
            String parameterType = element.attributeValue("parameterType");
            String resultType = element.attributeValue("resultType");
            String sql = element.getTextTrim();
            String key = namespace + "." + id;
            mappedStatement.setId(id);
            mappedStatement.setParamterType(parameterType==null ? null : getClassType(parameterType));
            mappedStatement.setResultType(getClassType(resultType));
            mappedStatement.setSql(sql);
            this.configuration.getMaps().put(key,mappedStatement);
        }


    }

    private static Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        return Class.forName(parameterType);
    }
}
