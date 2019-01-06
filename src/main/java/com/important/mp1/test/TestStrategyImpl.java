package com.important.mp1.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.important.mp1.beans.Employee;
import com.important.mp1.mapper.EmployeeMapper;

/**
 * fieldStrategy：该策略约定了如何产出注入的sql,涉及insert,update以及wrapper内部的entity属性生成的 where
 * 条件 当前策略：IGNORED 
 * 默认策略：NOT_NULL
 * 
 * 当使用NOT_NULL时：想把字段修改为null，发现不生效，因为该策略会判断是否非空 
 * 修改为IGNORED后，可以设置为null，但是有问题，具体如下：
 */
public class TestStrategyImpl extends ServiceImpl<EmployeeMapper, Employee> implements TestStrategy {

	private ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");

	private EmployeeMapper employeeMapper = ioc.getBean("employeeMapper", EmployeeMapper.class);

	// 非更新字段会变为null
	@Test
	public void testUpdate0() {
		Employee employee = new Employee();
		employee.setId(7);
		employee.setLastName("mybatis");
		employee.setEmail("mp3@gugu.com");
		Integer updateById = employeeMapper.updateById(employee);
		System.out.println("result:" + updateById);
	}

	// 如果用了QueryWrapper查询会将非设值字段当做条件并设为null
	// WHERE last_name=? AND email=? AND gender=? AND age=?
	@Test
	public void testSelect4() {
		Employee e = new Employee();
		e.setAge(22);
		List<Employee> employee = employeeMapper.selectList(new QueryWrapper<>(e));
		System.out.println(employee);
	}

	// 这样是正确的
	@Test
	public void testUpdate1() {
		Employee employee = employeeMapper.selectById(7);
		employee.setLastName("myb");
		employee.setEmail("mp3@gugu.com");
		employee.setAge(null);
		Integer updateById = employeeMapper.updateById(employee);
		System.out.println("result:" + updateById);
	}

	// 这样是正确的
	@Test
	public void testUpdate3() {
		QueryWrapper<Employee> queryEmp = new QueryWrapper<>();
		queryEmp.lambda().eq(Employee::getAge, 22);
		Employee employee = employeeMapper.selectOne(queryEmp);
		// 空指针
//		Employee employee = getOne(queryEmp);
		employee.setLastName("呵呵");
		employee.setEmail("mp3@gugu.com");
//		employee.setAge(null);
		Integer updateById = employeeMapper.updateById(employee);
		System.out.println("result:" + updateById);
	}

}
