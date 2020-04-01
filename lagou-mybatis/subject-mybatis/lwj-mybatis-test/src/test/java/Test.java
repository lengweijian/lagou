import com.mybatis.core.Resources;
import com.mybatis.core.SqlSession;
import com.mybatis.core.SqlSessionFactory;
import com.mybatis.core.SqlSessionFactoryBuilder;
import pojo.User;

import java.io.InputStream;
import java.util.List;

public class Test {

    @org.junit.Test
    public void test() throws Exception {
        // 加载配置文件
        InputStream inputStream = Resources.getResourceStream("config/sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user1 = new User();
        user1.setId(1);
        User o = sqlSession.selectOne("mapper.UserMapper.selectOne", user1);
        System.out.println(o);

    }

    @org.junit.Test
    public void test02() throws Exception {
        // 加载配置文件
        InputStream inputStream = Resources.getResourceStream("config/sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> o = sqlSession.selectList("mapper.UserMapper.selectList");
        for (User user : o) {
            System.out.println(user);
        }

    }
}
