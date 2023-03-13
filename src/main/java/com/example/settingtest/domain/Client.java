package com.example.settingtest.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "supplier_id")
    private int supplierId;

    @Column(name = "use_yn")
    private String useYn;

    @Column(name = "register")
    private String register;

    @Column(name = "modifier")
    private String modifier;

    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @Column(name = "modifier_date")
    private LocalDateTime modifierDate;

    @Column(name = "name")
    private String name;

    @Column(name = "ceo_name")
    private String ceoName;

    @Column(name = "business_number")
    private String businessNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "person_charge")
    private String personCharge;

    @Column(name = "address")
    private String address;

    @Column(name = "business_type")
    private String businessType;

    @Column(name = "business_state")
    private String businessState;

    @Column(name = "week_day_closing_time")
    private int weekDayClosingTime;

    @Column(name = "weekend_closing_time")
    private int weekendClosingTime;

    @Column(name = "type")
    private String type;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "remark")
    private String remark;

    @Column(name = "reference_client_id")
    private int referenceClientId;
}
