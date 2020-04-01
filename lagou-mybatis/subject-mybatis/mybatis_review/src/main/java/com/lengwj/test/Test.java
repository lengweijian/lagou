package com.lengwj.test;

import com.lengwj.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * TODO
 *
 * @author lengweijian
 * @version 1.0
 * @date 2020-03-29 08:15
 */
public class Test {


    SqlSession sqlSession;

    @Before
    public void brfore() throws IOException {
        String path = "config/sqlMapConfig.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(path);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        sqlSession = sqlSessionFactory.openSession(true);
    }

    @org.junit.Test
    public void testFindAll() throws IOException {
        List<User> users = sqlSession.selectList("com.lengwj.mapper.UserMapper.findAll");
        for (User user : users) {
            System.out.println(user);
        }
        sqlSession.close();

    }


    @org.junit.Test
    public void testOneToMany() throws IOException {
        User user =  sqlSession.selectOne("com.lengwj.mapper.UserMapper.findOrdersByUserId",1);
        System.out.println(user);
        sqlSession.close();

    }
}
