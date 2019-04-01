package com.beeworkshop.utils.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisUtil {
	private static SqlSessionFactory sqlSessionFactory;

	private MybatisUtil() {

	}

	private static SqlSessionFactory getSqlSessionFactory() {
		String config = "mybatis-conf.xml";
		try {
			// 读取mybatis-conf.xml文件
			InputStream ips = Resources.getResourceAsStream(config);
			// 创建SqlSessionFactory类的实例——单例模式
			if (sqlSessionFactory == null) {
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(ips);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sqlSessionFactory;
	}

	// 创建Session实例
	public static SqlSession getSqlSession() {
		return MybatisUtil.getSqlSessionFactory().openSession();
	}
}
