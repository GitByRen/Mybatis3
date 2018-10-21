package com.important.mp1.test;

import org.junit.Test;

import com.important.mp1.beans.Employee;

public class TestActiveRecord {

	/**
	 * public class Employee extends Model<Employee>
	 * 
	 * AR：Active Record(活动记录)，简称AR，是一种领域模型模式，特点就是
	 * 一个模型类对应关系型数据库中的一个表，而模型类的一个实例对应表中的一条记录；
	 */
	@Test
	public void testArInsert() {
		Employee employee = new Employee();
		employee.setLastName("ar");
		employee.setEmail("sls@atguigu.com");
		employee.setGender(1);
		boolean result = employee.insert();
		System.out.println("result：" + result);
	}

}
