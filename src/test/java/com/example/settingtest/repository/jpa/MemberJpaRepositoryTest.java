package com.example.settingtest.repository.jpa;

import com.example.settingtest.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @BeforeEach
    public void setUp() {
        //setup before testing
        memberJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("이메일로 멤버 조회 JPA 테스트")
    void findByEmail() {
        //given : 멤버 정보가 주어졌을 때
        Member member = new Member();
        member.setEmail("eden@timf.co.kr");
        member.setName("배이든");
        member.setAddress("경기도 부천");
        member.setPassword("1123");

        memberJpaRepository.save(member);

        //when : 이메일로 단건조회를 하면
        Member memberResult = memberJpaRepository.findByEmail("eden@timf.co.kr");

        //then : 해당 유저 정보를 조회할 수 있다.
        assertThat(memberResult.getId()).isGreaterThanOrEqualTo(1);
        assertThat(memberResult.getEmail()).isEqualTo("eden@timf.co.kr");
        assertThat(memberResult.getName()).isEqualTo("배이든");
        assertThat(memberResult.getAddress()).isEqualTo("경기도 부천");
        assertThat(memberResult.getPassword()).isEqualTo("1123");

    }
}