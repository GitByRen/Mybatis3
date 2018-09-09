package com.important.HelloWorld.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.important.HelloWorld.bean.Employee;

public interface EmployeeMapperDynamicSql {

	public List<Employee> getEmpsTestInnerParameter(Employee employee);
	
	// 查询条件
	public List<Employee> getEmpsByConditionIf(Employee employee);

	public List<Employee> getEmpsByConditionTrim(Employee employee);

	public List<Employee> getEmpsByConditionChoose(Employee employee);

	public void updateEmp(Employee employee);

	public List<Employee> getEmpsByConditionForeach(@Param("ids") List<Integer> ids);
	
	public void addEmps(@Param("emps") List<Employee> emps);

}
