package com.important.HelloWorld.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.important.HelloWorld.bean.Employee;

public interface EmployeeMapper {
	
	// 告诉mybatis封装这个map的时候使用哪个属性作为map的key
	@MapKey("id")
	public Map<Integer, Employee> getEmpByLastNameLikeReturnMap(String lastName);
	
	public Map<String, Object> getEmpByIdReturnMap(Integer id);
	
	public List<Employee> getEmpsByLastNameLike(String lastName);
	
	public Employee getEmpByMap(Map<String, Object> map);
	
	public Employee getEmpByIdAndLastName(@Param("id") Integer id,@Param("lastName") String lastName);

	public Employee getEmpById(Integer id);

	public void addEmp(Employee employee);

	public int updateEmp(Employee employee);

	public void deleteEmpById(Integer id);
}
