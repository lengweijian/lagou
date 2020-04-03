import com.mybatis.core.Resources;
import com.mybatis.core.SqlSession;
import com.mybatis.core.SqlSessionFactory;
import com.mybatis.core.SqlSessionFactoryBuilder;
import mapper.UserMapper;
import org.junit.After;
import org.junit.Before;
import pojo.User;

import java.io.InputStream;
import java.util.List;

public class Test {

    SqlSession sqlSession;

    @Before
    public void brfore() throws Exception {
        // 加载配置文件
        InputStream inputStream = Resources.getResourceStream("config/sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(inputStream);
        this.sqlSession = sqlSessionFactory.openSession();
    }

    @After
    public void after(){
        this.sqlSession.close();
    }

    /**
     * 查询单条记录
     * @throws Exception
     */
    @org.junit.Test
    public void test() throws Exception {
        // 加载配置文件
        InputStream inputStream = Resources.getResourceStream("config/sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user1 = new User();
        user1.setId(1);
        User o = this.sqlSession.selectOne("mapper.UserMapper.selectOne", user1);
        System.out.println(o);

    }

    /**
     * 查询多条记录
     * @throws Exception
     */
    @org.junit.Test
    public void test02() throws Exception {

        List<User> o = sqlSession.selectList("mapper.UserMapper.selectList");
        for (User user : o) {
            System.out.println(user);
        }

    }


    /**
     * Mybatis mapper调用方式优化
     * @throws Exception
     */
    @org.junit.Test
    public void test03() throws Exception {
        User u = new User();
        u.setId(1);

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        for (User user : mapper.selectList()) {
            System.out.println(user);
        }

        System.out.println("selectone:" + mapper.selectOne(u));
    }


}
