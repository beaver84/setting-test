package com.example.settingtest.com.example.settingtest.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Lazy
@EnableTransactionManagement
public class RdbConnectionConfiguration {

    @Bean(name = "transactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(
            @Qualifier("mybatisTransactionManager") PlatformTransactionManager mybatisTransactionManager,
            @Qualifier("teamflaceJpaTransactionManager") PlatformTransactionManager teamflaceJpaTransactionManager) {
        return new ChainedTransactionManager(
                mybatisTransactionManager,
                teamflaceJpaTransactionManager);
    }
}
