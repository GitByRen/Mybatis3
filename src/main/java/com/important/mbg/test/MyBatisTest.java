package com.important.mbg.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.important.mbg.bean.Employee;
import com.important.mbg.bean.EmployeeExample;
import com.important.mbg.bean.EmployeeExample.Criteria;
import com.important.mbg.dao.EmployeeMapper;

public class MyBatisTest {

	public SqlSessionFactory getSessionFactory() throws IOException {
		String resources = "mybatis-config.xml";
		InputStream resourceAsStream = Resources.getResourceAsStream(resources);
		return new SqlSessionFactoryBuilder().build(resourceAsStream);
	}

	@Test
	public void testMbg() throws Exception {
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		File configFile = new File("generatorConfig.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
	}

	@Test
	public void testMyBatis3Simple() throws IOException {
		SqlSessionFactory sessionFactory = getSessionFactory();
		SqlSession openSession = sessionFactory.openSession();
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			Employee employee = mapper.selectByPrimaryKey(1);
			System.out.println(employee);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			openSession.close();
		}
	}

	@Test
	public void testMyBatis3() throws Exception {
		SqlSessionFactory sessionFactory = getSessionFactory();
		SqlSession openSession = sessionFactory.openSession();
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			// xxxExample就是封装查询条件的
			List<Employee> selectAll = mapper.selectByExample(null);
			for (Employee employee : selectAll) {
				System.out.println(employee);
			}

			System.out.println("************************");
			
			// 封装员工查询条件的example
			EmployeeExample example = new EmployeeExample();
			// 拼装查询条件，名字中带有e，员工性别是1的
			Criteria criteria = example.createCriteria();
			criteria.andLastNameLike("%e%");
			criteria.andGenderEqualTo("1");
			
			// or email like '%e%'
			Criteria criteria2 = example.createCriteria();
			criteria2.andEmailLike("%e%");
			example.or(criteria2);
			
			List<Employee> selectByExample = mapper.selectByExample(example);
			for (Employee employee : selectByExample) {
				System.out.println(employee);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			openSession.close();
		}
	}
	
}
