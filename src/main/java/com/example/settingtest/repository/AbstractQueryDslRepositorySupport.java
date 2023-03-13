package com.example.settingtest.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public abstract class AbstractQueryDslRepositorySupport extends QuerydslRepositorySupport{

	public AbstractQueryDslRepositorySupport(Class<?> domainClass) {
		super(domainClass);
	}

}
