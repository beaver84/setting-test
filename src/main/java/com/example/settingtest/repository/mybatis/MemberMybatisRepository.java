package com.example.settingtest.repository.mybatis;

import com.example.settingtest.domain.Member;
import com.example.settingtest.repository.AbstractMybatisRepository;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberMybatisRepository extends AbstractMybatisRepository {

    private Logger log = LogManager.getLogger(this.getClass());

    /**
     * 애플리케이션 시작 시 MyBatis 설정 파일을 참고하여 SqlSessionFactory이 생성된다.
     * 매 요청마다 AbstractMybatisRepository에서 SqlSession 객체를 생성한다.
     * SqlSession 객체를 통해 mapper에 정의되어 있는 DB 작업을 진행한다.
     */
    @Qualifier("mybatisSqlSessionFactory")
    @Autowired
    @Override
    protected void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Autowired
    @Override
    protected void setMapperPrefix() {
        this.mapperPrefix = "com.example.settingtest.repository.mapper.memberMapper.";
    }

    public List<Member> findAllUser() {
        return selectList("findAllUser");
    }
}
