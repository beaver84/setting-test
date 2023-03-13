package com.example.settingtest.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;

    private String password;

    private String name;

    private String address;
}
