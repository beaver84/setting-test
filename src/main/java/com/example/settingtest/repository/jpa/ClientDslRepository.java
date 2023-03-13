package com.example.settingtest.repository.jpa;

import com.example.settingtest.domain.Client;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientDslRepository {
    List<Client> findByName(String name);
}
