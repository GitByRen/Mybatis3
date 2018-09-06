package com.important.HelloWorld.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.important.HelloWorld.bean.Employee;
import com.important.HelloWorld.dao.EmployeeMapper;
import com.important.HelloWorld.dao.EmployeeMapperAnnotation;

/**
 * 1.SqlSession是非线程安全的，每次使用都应该获取新的对象
 * 2.mapper接口没有实现类，但是mybatis会为这个接口生成一个代理对象
 */
public class MyBatisTest {
	
	public SqlSessionFactory getSessionFactory() throws IOException {
		String resources = "mybatis-config.xml";
		InputStream resourceAsStream = Resources.getResourceAsStream(resources);
		return new SqlSessionFactoryBuilder().build(resourceAsStream);
	}

	@Test
	public void test() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			// EmployeeMapper.xml中namespace+id
			Employee employee = openSession.selectOne("com.important.HelloWorld.dao.EmployeeMapper.getEmpById", 1);
			System.out.println(employee);
		} finally {
			openSession.close();
		}
	}

	/**
	 * 1.接口式编程：利于解耦，参数类型检查
	 */
	@Test
	public void test1() throws IOException {
		// 1.获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSessionFactory();
		// 2.获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession();
		// 3.会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			Employee empById = mapper.getEmpById(1);
			System.out.println(empById);
		} finally {
			openSession.close();
		}
	}
	
	/**
	 * 2.注解式sql
	 */
	@Test
	public void test2() throws IOException {
		SqlSessionFactory sessionFactory = getSessionFactory();
		SqlSession openSession = sessionFactory.openSession();
		try {
			EmployeeMapperAnnotation mapper = openSession.getMapper(EmployeeMapperAnnotation.class);
			Employee empById = mapper.getEmpById(1);
			System.out.println(empById);
		} finally {
			openSession.close();
		}
	}
	
	/**
	 * 3.测试增删改
	 * 	1）、mybatis允许增删改直接定义以下类型的返回值
	 * 		Integer、Long、Boolean、void
	 * 	2）、sessionFactory.openSession(true) ==> 可以自动提交
	 */
	@Test
	public void test3() throws IOException {
		SqlSessionFactory sessionFactory = getSessionFactory();
		// 获取到的openSession不会自动提交
		SqlSession openSession = sessionFactory.openSession();
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			
			// 添加
			Employee employee = new Employee(null, "jerry", "jerry@abc.com", "1");
			mapper.addEmp(employee);
			// useGeneratedKeys、keyProperty
			System.out.println(employee.getId());
			
			// 修改
//			Employee employee = new Employee(1, "jerrys", "jerry@abc.com", "0");
//			mapper.updateEmp(employee);
			
			// 删除
//			mapper.deleteEmpById(2);
			
			// 手动提交
			openSession.commit();
		} finally {
			openSession.close();
		}
	}
}
