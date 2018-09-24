package com.important.mp1.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.important.mp1.beans.Employee;

/**
 * 继承BaseMapper即可,
 * BaseMapper<T>指定的是当前Mapper接口所操作的实体类类型
 */
public interface EmployeeMapper extends BaseMapper<Employee>{

}
