package com.important.mp.test1;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMp {

	private ApplicationContext ioc = 
			new ClassPathXmlApplicationContext("applicationContext.xml");

	@Test
	public void testDataSource() {
		DataSource ds = ioc.getBean("dataSource", DataSource.class);
		System.out.println(ds);
	}

}
