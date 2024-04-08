package com.example.batch.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Manager {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, unique = true)
    private String managerId;

    @Column(nullable = false)
    private String managerName;

    @Column(nullable = false)
    private String managerPhoneNo;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false, unique = true)
    private String managerEmail;

    @Column(nullable = false)
    private String managerPassword;

    @Column(nullable = false)
    private String managerAuthority;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;

    @OneToMany(mappedBy = "manager")
    private List<Answer> answerList = new ArrayList<>();

    @OneToMany(mappedBy = "manager")
    private List<Notice> noticeList = new ArrayList<>();

}