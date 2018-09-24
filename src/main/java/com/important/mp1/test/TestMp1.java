package com.important.mp1.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baomidou.mybatisplus.plugins.Page;
import com.important.mp1.beans.Employee;
import com.important.mp1.mapper.EmployeeMapper;

public class TestMp1 {

	private ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");

	private EmployeeMapper employeeMapper = ioc.getBean("employeeMapper", EmployeeMapper.class);

	/**
	 * 通用 删除操作
	 */
	@Test
	public void testCommonDelete() {
		// 1.根据id删除
		Integer deleteById = employeeMapper.deleteById(5);
		System.out.println("deleteById：" + deleteById);

		// 2.根据条件删除
		Map<String, Object> columnMap = new HashMap<>();
		columnMap.put("last_name", "mp1");
		columnMap.put("email", "mp1@guigu.com");
		Integer deleteByMap = employeeMapper.deleteByMap(columnMap);
		System.out.println("deleteByMap：" + deleteByMap);

		// 3.批量删除
		Integer deleteBatchIds = employeeMapper.deleteBatchIds(Arrays.asList(7, 8, 9));
		System.out.println(deleteBatchIds);
	}

	/**
	 * 通用 查询操作
	 */
	@Test
	public void testCommonSelect() {
		// 1.通过id来查
		Employee selectById = employeeMapper.selectById(1);
		System.out.println("selectById：" + selectById);

		// 2.通过多个列进行查询 id + lastName
		Employee employee = new Employee();
		employee.setId(1);
		employee.setLastName("tom");
		Employee selectOne = employeeMapper.selectOne(employee);
		System.out.println("selectOne：" + selectOne);

		// 3.通过多个id进行查询
		List<Employee> selectBatchIds = employeeMapper.selectBatchIds(Arrays.asList(1, 2, 4));
		System.out.println("selectBatchIds：" + selectBatchIds);

		// 4.通过map封装条件查询，要写数据库字段名
		Map<String, Object> columnMap = new HashMap<>();
		columnMap.put("last_name", "mp");
		columnMap.put("gender", 1);
		List<Employee> selectByMap = employeeMapper.selectByMap(columnMap);
		System.out.println("selectByMap：" + selectByMap);

		// 5.分页查询，不是真实的分页，sql没有limit
		List<Employee> selectPage = employeeMapper.selectPage(new Page<>(1, 2), null);
		System.out.println("selectPage：" + selectPage);
	}

	/**
	 * 通用 更新操作
	 */
	@Test
	public void testCommonUpdate() {
		Employee employee = new Employee();
		employee.setId(7);
		employee.setLastName("mybatis");
		employee.setEmail("mp2@guigu.com");
		employee.setGender(1);
		employee.setAge(41);
		// update:会进行非空判断
		Integer updateById = employeeMapper.updateById(employee);

		// 更新所有，没有就是null
//		employeeMapper.updateAllColumnById(employee);

		System.out.println("result:" + updateById);
	}

	/**
	 * 通用 插入操作
	 */
	@Test
	public void testCommonInsert() {
		Employee employee = new Employee();
		employee.setLastName("mp1");
		employee.setEmail("mp1@guigu.com");
		employee.setGender(2);
		employee.setAge(11);
		employee.setSalary(201.21);
		// insert:会根据实体类的每个属性进行非空判断，只有非空的属性才会出现在sql语句中
		Integer result = employeeMapper.insert(employee);
		System.out.println("result:" + result);

		// insertAllColumn:不管属性是否非空，属性所对应的字段都会出现到sql语句中
//		employeeMapper.insertAllColumn(employee);

		// 自动获取主键值
		Integer id = employee.getId();
		System.out.println("key:" + id);
	}

}
