package com.example.settingtest.repository.jpa.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.example.settingtest.domain.Client;
import com.example.settingtest.domain.QClient;
import com.example.settingtest.repository.AbstractQueryDslRepositorySupport;
import com.example.settingtest.repository.jpa.ClientDslRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ClientDslRepositoryImpl extends AbstractQueryDslRepositorySupport implements ClientDslRepository {

    @Autowired
    public ClientDslRepositoryImpl() {
        super(Client.class);
    }

    @Autowired
    @Override
    public void setEntityManager(@Qualifier("teamfleshEntityManagerFactory") EntityManager entityManager) {
        super.setEntityManager(entityManager);
    }

    @Override
    public List<Client> findByName(String name) {
        JPAQueryFactory query = new JPAQueryFactory(super.getEntityManager());

        QClient qClient = QClient.client;

        return query
                .selectFrom(qClient)
                .where(qClient.name.eq(name))
                .orderBy(qClient.startDate.desc())
                .fetch();
    }
}
