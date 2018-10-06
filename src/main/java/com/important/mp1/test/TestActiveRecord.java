package com.important.mp1.test;

import org.junit.Test;

import com.important.mp1.beans.Employee;

public class TestActiveRecord {

	/**
	 * AR插入
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
