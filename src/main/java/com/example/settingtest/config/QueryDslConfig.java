package com.example.settingtest.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
@RequiredArgsConstructor
public class QueryDslConfig {

    @PersistenceContext(unitName="settingTestEntityManagerFactory")
    private final EntityManager em;

    @Bean(name="appQueryDslFactory")
    public JPAQueryFactory queryFactory() {
        return new JPAQueryFactory(em);
    }

}

