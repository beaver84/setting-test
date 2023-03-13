package com.example.settingtest.repository.jpa;

import com.example.settingtest.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);


}

