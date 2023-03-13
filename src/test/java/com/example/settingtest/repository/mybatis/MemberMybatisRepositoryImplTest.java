package com.example.settingtest.repository.mybatis;

import com.example.settingtest.domain.Member;
import com.example.settingtest.repository.mybatis.MemberMybatisRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberMybatisRepositoryImplTest {

    @Autowired
    MemberMybatisRepository memberMybatisRepository;

    @Test
    @DisplayName("멤버 조회 Mybatis 테스트")
    void findByName() {

        //given & when
        List<Member> allUser = memberMybatisRepository.findAllUser();

        //then
        assertThat(allUser).extracting(Member::getName).containsAnyOf("배이든");
        assertThat(allUser).extracting(Member::getAddress).containsAnyOf("구로주공아파트");
        assertThat(allUser).extracting(Member::getEmail).containsAnyOf("eden@timf.co.kr");

    }
}