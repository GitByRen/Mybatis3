package com.important.HelloWorld.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.important.HelloWorld.bean.Department;
import com.important.HelloWorld.bean.Employee;
import com.important.HelloWorld.dao.EmployeeMapperDynamicSql;

public class MyBatisTest1 {

	public SqlSessionFactory getSessionFactory() throws IOException {
		String resources = "mybatis-config.xml";
		InputStream resourceAsStream = Resources.getResourceAsStream(resources);
		return new SqlSessionFactoryBuilder().build(resourceAsStream);
	}

	@Test
	public void testDynamicSql() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapperDynamicSql mapper = openSession.getMapper(EmployeeMapperDynamicSql.class);
			Employee employee = new Employee(1, "%e%", "jerry@abc.com", null);
			
			// 查询的时候某些条件没带可能sql拼装会有问题
			// 1.给where后面加上1=1，以后的条件都用and xxx.
			// 2.mybatis使用where标签将所有的查询条件包括在内，where只会去掉第一个多出来的and或者or
			List<Employee> empsByConditionIf = mapper.getEmpsByConditionIf(employee);
			System.out.println(empsByConditionIf);
			
			// trim：自定义字符串的截取规则
			List<Employee> empsByConditionTrim = mapper.getEmpsByConditionTrim(employee);
			System.out.println(empsByConditionTrim);
			
			// choose
			List<Employee> empsByConditionChoose = mapper.getEmpsByConditionChoose(employee);
			System.out.println(empsByConditionChoose);
			
			// udpate
//			mapper.updateEmp(employee);
			
			List<Employee> empsByConditionForeach = mapper.getEmpsByConditionForeach(Arrays.asList(1, 2, 3));
			System.out.println(empsByConditionForeach);
			
//			openSession.commit();
		} finally {
			openSession.close();
		}
	}

	
	@Test
	public void testBatchSave() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapperDynamicSql mapper = openSession.getMapper(EmployeeMapperDynamicSql.class);
			List<Employee> emps = new ArrayList<>();
			emps.add(new Employee(null, "123", "132@qq.com", "1", new Department(1)));
			emps.add(new Employee(null, "567", "ggh@163.com", "0", new Department(2)));
			mapper.addEmps(emps);
			openSession.commit();
		} finally {
			openSession.close();
		}
	}
	
	
	@Test
	public void testInnerParam() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapperDynamicSql mapper = openSession.getMapper(EmployeeMapperDynamicSql.class);
			Employee employee = new Employee(1, "smith", "jerry@abc.com", null);
			List<Employee> empsTestInnerParameter = mapper.getEmpsTestInnerParameter(employee);
			System.out.println(empsTestInnerParameter);
		} finally {
			openSession.close();
		}
	}
}
