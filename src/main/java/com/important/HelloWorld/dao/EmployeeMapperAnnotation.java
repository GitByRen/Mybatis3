package com.important.HelloWorld.dao;

import org.apache.ibatis.annotations.Select;

import com.important.HelloWorld.bean.Employee;

public interface EmployeeMapperAnnotation {

	@Select("select * from tbl_employee where id = #{id}")
	public Employee getEmpById(Integer id);
	
}
