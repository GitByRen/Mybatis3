package com.important.plugin.plugin;

import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

/**
 * 完成插件签名
 */
@Intercepts({ 
	@Signature(type = StatementHandler.class, 
			method = "parameterize", 
			args = java.sql.Statement.class) 
	})
public class MyFirstPlugin implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// 执行目标方法
		Object proceed = invocation.proceed();
		return proceed;
	}

	/**
	 * 包装目标对象的：为目标对象创建一个代理对象
	 */
	@Override
	public Object plugin(Object target) {
		Object wrap = Plugin.wrap(target, this);
		// 返回为当前target创建的动态代理
		return wrap;
	}

	@Override
	public void setProperties(Properties properties) {
		System.out.println(properties);
	}

}
