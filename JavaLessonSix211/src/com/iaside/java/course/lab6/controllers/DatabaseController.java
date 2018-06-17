package com.iaside.java.course.lab6.controllers;

import com.iaside.java.course.lab6.models.mappers.GetAllMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseController {
    private SqlSessionFactory sqlSessionFactory;
    private static DatabaseController databaseController;
    private String driver = "com.mysql.cj.jdbc.Driver";

    private DatabaseController(){

    }

    public static DatabaseController getInstance() throws IOException {
        if(databaseController == null){
            synchronized(DatabaseController.class) {
                //单例双重检查锁
                databaseController = new DatabaseController();
            }
        }
        return databaseController;
    }

    public void connect(String url, String user, String pwd) throws IOException {
        String resource = "com/iaside/java/course/lab6/models/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        Properties p = new Properties();
        p.setProperty("driver", driver);
        p.setProperty("url", url);
        p.setProperty("username", user);
        p.setProperty("password", pwd);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, p);
        sqlSessionFactory.getConfiguration().addMapper(GetAllMapper.class);
    }


    public SqlSession createSession(){
        return sqlSessionFactory.openSession();
    }



    public static void main(String[] args) throws IOException {

//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//            BlogMapper mapper = session.getMapper(BlogMapper.class);
//            Blog blog = mapper.selectBlog(101);
//        } finally {
//            session.close();
//        }
    }
}
