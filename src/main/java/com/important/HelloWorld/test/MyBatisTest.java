package com.important.HelloWorld.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.important.HelloWorld.bean.Department;
import com.important.HelloWorld.bean.Employee;
import com.important.HelloWorld.dao.DepartmentMapper;
import com.important.HelloWorld.dao.EmployeeMapper;
import com.important.HelloWorld.dao.EmployeeMapperAnnotation;
import com.important.HelloWorld.dao.EmployeeMapperPlus;

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
	
	/**
	 * 4.处理单个参数，多个参数
	 */
	@Test
	public void test4() throws IOException {
		SqlSessionFactory sessionFactory = getSessionFactory();
		SqlSession openSession = sessionFactory.openSession();
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			//Employee employee = mapper.getEmpByIdAndLastName(3, "jerry");
			
			Map<String, Object> map = new HashMap<>();
			map.put("id", 3);
			map.put("lastName", "Jerry");
			map.put("tableName", "tbl_employee");
			Employee employee = mapper.getEmpByMap(map);
			System.out.println(employee);
		} finally {
			openSession.close();
		}
	}
	
	/**
	 * 5.测试返回类型为List<Employee>、Map、Map<Integer, Employee>
	 */
	@Test
	public void test5() throws IOException {
		SqlSessionFactory sessionFactory = getSessionFactory();
		SqlSession openSession = sessionFactory.openSession();
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			List<Employee> employee = mapper.getEmpsByLastNameLike("%e%");
			System.out.println(employee);
			
			Map<String, Object> returnMap = mapper.getEmpByIdReturnMap(1);
			System.out.println(returnMap);
			
			Map<Integer, Employee> likeReturnMap = mapper.getEmpByLastNameLikeReturnMap("%r%");
			System.out.println(likeReturnMap);
		} finally {
			openSession.close();
		}
	}
	
	/**
	 * 6.测试ResultMap,association
	 */
	@Test
	public void test6() throws IOException {
		SqlSessionFactory sessionFactory = getSessionFactory();
		SqlSession openSession = sessionFactory.openSession();
		try {
			EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);
			Employee empById = mapper.getEmpById(1);
			System.out.println(empById);
			
			Employee empAndDept = mapper.getEmpAndDept(3);
			System.out.println(empAndDept);
			System.out.println(empAndDept.getDept());
			
			Employee empByIdStep = mapper.getEmpByIdStep(1);
			System.out.println(empByIdStep);
			System.out.println(empByIdStep.getDept());
		} finally {
			openSession.close();
		}
	}
	
	
	/**
	 * 7.测试collection
	 */
	@Test
	public void test7() throws IOException {
		SqlSessionFactory sessionFactory = getSessionFactory();
		SqlSession openSession = sessionFactory.openSession();
		try {
			DepartmentMapper mapper = openSession.getMapper(DepartmentMapper.class);
			
			Department deptByIdPlus = mapper.getDeptByIdPlus(1);
			System.out.println(deptByIdPlus);
			System.out.println(deptByIdPlus.getEmps());
			
			// 分步查询
			Department deptByIdStep = mapper.getDeptByIdStep(2);
			System.out.println(deptByIdStep);
			System.out.println(deptByIdStep.getEmps());
		} finally {
			openSession.close();
		}
	}
	
}
