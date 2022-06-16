package com.hyundai.kosafinal.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan(basePackages="com.hyundai.kosafinal.mapper.userorder",sqlSessionFactoryRef="db2SqlSessionFactory")
@EnableTransactionManagement
public class UserOrderDatabaseConfig {

  @Bean(name="db2DataSource")
  @ConfigurationProperties(prefix="spring.db2.datasource")
  public DataSource db2DataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name="db2SqlSessionFactory")
  public SqlSessionFactory sqlSessionFactory(@Qualifier("db2DataSource") DataSource db2DataSource, ApplicationContext applicationContext) throws Exception{
    final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(db2DataSource);
    sessionFactory.setMapperLocations(applicationContext.getResources("classpath:mybatis/mapper/userorder/*.xml"));
    return sessionFactory.getObject();
  }

  @Bean(name="db2SqlSessionTemplate")
  public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory db2sqlSessionFactory) throws Exception{
    return new SqlSessionTemplate(db2sqlSessionFactory);
  }

  @Bean(name = "db2transactionManager")
  public PlatformTransactionManager transactionManager(@Qualifier("db2DataSource") DataSource db2DataSource) {
    return new DataSourceTransactionManager(db2DataSource);
  }
}
