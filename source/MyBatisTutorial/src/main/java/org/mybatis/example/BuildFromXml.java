package org.mybatis.example;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * Hello world!
 *
 */
public class BuildFromXml {
	public static void main(String[] args) {
		try {

			System.out.println("Hello World!");
			String resource = "org/mybatis/example/mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			if (sqlSessionFactory != null) {
				sqlSessionFactory = null;
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
