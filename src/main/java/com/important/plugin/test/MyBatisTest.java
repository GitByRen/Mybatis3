package com.important.plugin.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.important.mbg.bean.Employee;
import com.important.mbg.dao.EmployeeMapper;

public class MyBatisTest {

	public SqlSessionFactory getSessionFactory() throws IOException {
		String resources = "mybatis-config.xml";
		InputStream resourceAsStream = Resources.getResourceAsStream(resources);
		return new SqlSessionFactoryBuilder().build(resourceAsStream);
	}


	@Test
	public void testMyPlugin() throws IOException {
		SqlSessionFactory sessionFactory = getSessionFactory();
		SqlSession openSession = sessionFactory.openSession();
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			Employee employee = mapper.selectByPrimaryKey(1);
			System.out.println(employee);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			openSession.close();
		}
	}

	
	/**
	 * 插件原理
	 * 在四大对象创建的时候
	 * 1、每个创建出来的对象不是直接返回的，而是
	 * 		interceptorChain.pluginAll(parameterHandler);
	 * 2、获取到所有的Interceptor（拦截器）（插件需要实现的接口）；
	 * 		调用interceptor.plugin(target);返回target包装后的对象
	 * 3、插件机制，我们可以使用插件为目标对象创建一个代理对象；AOP（面向切面）
	 * 		我们的插件可以为四大对象创建出代理对象；
	 * 		代理对象就可以拦截到四大对象的每一个执行；
	 * 
		public Object pluginAll(Object target) {
		    for (Interceptor interceptor : interceptors) {
		      target = interceptor.plugin(target);
		    }
		    return target;
		  }
	 */
	@Test
	public void testPlugin() {
		/**
		 * 插件编写：
		 * 1、编写Interceptor的实现类
		 * 2、使用@Intercepts注解完成插件签名
		 * 3、将写好的插件注册到全局配置文件中
		 */
	}
}
