package com.example.settingtest.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Lazy
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.example.settingtest.repository.jpa", entityManagerFactoryRef = "settingTestEntityManagerFactory")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class JpaConfig {

    private Logger log = LogManager.getLogger(this.getClass());

    private Properties properties;

    @Autowired
    public JpaConfig(@Qualifier("settingTestDatasourceProperties") Properties properties) {
        this.properties = properties;
    }

    //application.properties 파일에 teamflace.jpa로 시작하는 접속 정보를 Properties에 가져온다.
    @ConfigurationProperties(prefix = "settingTest.jpa")
    @Bean(name = "settingTestJpaDataSourceProperties")
    public Properties settingTestJpaDataSourceProperties() {
        return new Properties();
    }

    //위에서 만들어진 properties 정보를 바탕으로 JDBC에 필요한 dataSource를 생성
    @Bean(name = "settingTestJpaDatasource")
    public DataSource settingTestJpaDatasource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        log.info("settingTestJpaDatasource  ===> {}", dataSource);
        dataSource.setDriverClassName(properties.getProperty("driver-class-name"));
        dataSource.setUrl(properties.getProperty("url"));
        dataSource.setUsername(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));

        return dataSource;
    }

    //위에서 만들어진 datasource를 바탕으로 JPA에 영속성 컨텍스트인 EntityManager를 만든다.
    @Bean(name = "settingTestEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean settingTestEntityManagerFactory() throws Exception {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(settingTestJpaDatasource());
        em.setPackagesToScan("com.example.settingtest.domain");
        //JPA 로그에서 show-sql 설정을 위해 추가
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(settingTestJpaDataSourceProperties());
        log.info("settingTestJpaDataSourceProperties ===> {}", settingTestJpaDataSourceProperties());

        return em;
    }

    //Mybatis와 같이 사용하기 위해 작업을 한 트랜잭션으로 묶기 위한 배경작업
    @Bean(name = "settingTestJpaTransactionManager")
    public PlatformTransactionManager settingTestJpaTransactionManager() throws Exception {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(settingTestEntityManagerFactory().getObject());
        log.info("settingTestJpaTransactionManager  ===> {}", transactionManager);
        return transactionManager;
    }
}

