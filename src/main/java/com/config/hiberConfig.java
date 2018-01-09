package com.config;


import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.DaoImpl.*;
import com.model.*;

@Configuration
@ComponentScan("com")
@EnableTransactionManagement

public class hiberConfig 
{
	@Autowired
	@Bean(name="datasource")
	public DataSource getH2Data()
	{
		DriverManagerDataSource dsource = new DriverManagerDataSource();
		dsource.setDriverClassName("org.h2.Driver");
		dsource.setUrl("jdbc:h2:tcp://localhost/~/CakeWorld");
		dsource.setUsername("som");
		dsource.setPassword("som");
		System.out.println("H2 Connected");
		return dsource;
		
	}
	
	private Properties getHiber()
	{
		Properties p = new Properties();
		p.put("hibernate.dialect","org.hibernate.dialect.H2Dialect");
		p.put("hibernate.hbm2ddl.auto", "update");
		p.put("hibernate.show_sql", "true");
		
		System.out.println("Table Created in H2");
		return p;
	}
	
	@Autowired
	@Bean (name="sessionFactory")
	public SessionFactory getSessionFac (DataSource dataSource)
	{
		LocalSessionFactoryBuilder lsfb= new LocalSessionFactoryBuilder(dataSource);
		lsfb.addProperties(getHiber());
		lsfb.addAnnotatedClass(User.class);
		lsfb.addAnnotatedClass(Supplier.class);
		lsfb.addAnnotatedClass(Category.class);
		lsfb.addAnnotatedClass(Product.class);
				
		return lsfb.buildSessionFactory();
		
	}
	
	@Autowired
	@Bean (name="UserDaoImpl")
	public UserDaoImpl saveUserData(SessionFactory sf)
	{
		return new UserDaoImpl(sf);
	}
	
	
	@Autowired
	@Bean(name="SupplierDaoImpl")
	public SupplierDaoImpl saveSuppData (SessionFactory sf)
	{
		return new SupplierDaoImpl(sf);
	}
	
		
	@Autowired
	@Bean (name="CategoryDaoImpl")
	public CategoryDaoImpl saveCatData(SessionFactory sf)
	{
		return new CategoryDaoImpl(sf);
	}
	
	@Autowired
	@Bean(name="transationManager")
	public HibernateTransactionManager gettrans (SessionFactory sf)
	{
		HibernateTransactionManager tm = new HibernateTransactionManager(sf);
		return tm;
	}
	
}