package com.example.settingtest.repository.jpa.impl;

import com.example.settingtest.domain.Client;
import com.example.settingtest.repository.jpa.ClientDslRepository;
import com.example.settingtest.repository.jpa.ClientJpaRepository;
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
class ClientDslRepositoryImplTest {

    @Autowired
    ClientDslRepository clientDslRepository;

    @Autowired
    ClientJpaRepository clientJpaRepository;

    @BeforeEach
    public void setUp() {
        //setup before testing
        clientJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("매출처 조회 QueryDsl 테스트")
    void findByName() {

        //given
        Client client = new Client();
        client.setName("aaa");
        client.setAddress("서울시");
        client.setPhoneNumber("01000000000");
        client.setRegister("admin");
        client.setType("본사");
        client.setUseYn("Y");
        client.setRegisterDate(LocalDateTime.now());

        clientJpaRepository.save(client);

        //when
        List<Client> clientList = clientDslRepository.findByName("aaa");

        //then
        assertThat(clientList.get(0).getName()).isEqualTo("aaa");
        assertThat(clientList.get(0).getAddress()).isEqualTo("서울시");
        assertThat(clientList.get(0).getPhoneNumber()).isEqualTo("01000000000");
    }
}