package com.important.HelloWorld.dao;

import com.important.HelloWorld.bean.Department;

public interface DepartmentMapper {

	public Department getDeptById(Integer id);
	
	public Department getDeptByIdPlus(Integer id);

	public Department getDeptByIdStep(Integer id);
}
