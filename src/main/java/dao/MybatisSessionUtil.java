package dao;

import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;

/**
 * @author YachenGuo
 * @ClassName ConnFactory
 * @Description
 * @createTime 2021 09 07 16:10
 */
public class MybatisSessionUtil {

    static SqlSessionFactory sqlSessionFactory = null;

    public static SqlSession getSession() {

        if(sqlSessionFactory == null) {
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            try{
                sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        return sqlSessionFactory.openSession();
    }
        else {
            return sqlSessionFactory.openSession();
        }

    }


}
