package com.important.mp1.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.important.mp1.beans.Employee;
import com.important.mp1.mapper.EmployeeMapper;

public class TestMp2 {

	private ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");

	private EmployeeMapper employeeMapper = ioc.getBean("employeeMapper", EmployeeMapper.class);

	@Test
	public void testEntityWrapperSelect() {
		List<Employee> selectPage = employeeMapper.selectPage(new Page<Employee>(1, 2),
				new EntityWrapper<Employee>().between("age", 18, 30).eq("gender", 1).eq("last_name", "Tom"));
		System.out.println("selectPage:" + selectPage);
		
		// or()   :(gender = ? and last_name like ? or email like ?)
		// orNew():(gender = ? and last_name like ?) or (email like ?)
		List<Employee> selectList = employeeMapper.selectList(
				new EntityWrapper<Employee>().eq("gender", 0).like("last_name", "老师").or().like("email", "a"));
		System.out.println("selectList:" + selectList);
	}

}
