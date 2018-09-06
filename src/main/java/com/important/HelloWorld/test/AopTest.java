package com.important.HelloWorld.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AopTest {

	public static void main(String[] args) {
//		EmployeeMapper e = (EmployeeMapper) getAop(new EmployeeMapper() {
//			
//			@Override
//			public Employee getEmpById(Integer id) {
//				Employee employee = new Employee();
//				employee.setId(1);
//				employee.setLastName("ab");
//				return employee;
//			}
//		});
//		System.out.println(e.getEmpById(1));
	}
	
	public static Object getAop(Object target) {
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
				new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						Object invoke = method.invoke(target, args);
						return invoke;
					}
				});
	}

}
