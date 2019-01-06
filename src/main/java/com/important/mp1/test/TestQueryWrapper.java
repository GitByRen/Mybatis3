package com.important.mp1.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.important.mp1.beans.Employee;
import com.important.mp1.mapper.EmployeeMapper;

public class TestQueryWrapper {

	private ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");

	private EmployeeMapper employeeMapper = ioc.getBean("employeeMapper", EmployeeMapper.class);

	/**
	 * 查询
	 * QueryWrapper：条件构造器，需要写数据库字段名
	 */
	@Test
	public void testEntityWrapperSelect() {
		// 1.分页查询tbl_employee中，年龄在18-30之间性别为男且姓名为Tom的所有用户
		IPage<Employee> selectPage = employeeMapper.selectPage(new Page<Employee>(1, 2),
				new QueryWrapper<Employee>().between("age", 18, 30).eq("gender", 1).eq("last_name", "Tom"));
		System.out.println("selectPage:" + selectPage);

		// 2.查询tbl_employee中，性别为女并且名字中带有“老师”或者邮箱中带有“a”的记录
		List<Employee> selectList = employeeMapper.selectList(
				new QueryWrapper<Employee>().eq("gender", 0).like("last_name", "老师").or().like("email", "a"));
		// or() :(gender = ? and last_name like ? or email like ?)
		// orNew():(gender = ? and last_name like ?) or (email like ?)
		System.out.println("selectList:" + selectList);
		
		// 3.查询性别为女，根据age进行排序(asc/desc)，简单分页
		List<Employee> selectList2 = employeeMapper.selectList(new QueryWrapper<Employee>()
				.eq("gender", 0)
				// 默认升序
//				.orderBy("age")
				.orderByDesc("age")
//				.last("desc limit 1,3") 添加到sql后面，有sql注入风险
				);
		System.out.println(selectList2);
		
	}
	
	
	/**
	 * 更新或删除
	 */
	@Test
	public void testEntityWrapperUpdateOrDelete() {
		Employee employee = new Employee();
		employee.setLastName("哈哈");
		employee.setEmail("cls@sina.com");
		employee.setGender(0);
		employeeMapper.update(employee, new QueryWrapper<Employee>()
				.eq("last_name", "Tom"));
		
		// 删除
		employeeMapper.delete(new QueryWrapper<Employee>()
				.eq("last_name", "Tom"));
	}

}