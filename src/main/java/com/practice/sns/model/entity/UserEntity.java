package com.practice.sns.model.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table
@Getter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "username")
    private String userName;
    @Column(name = "password")
    private String password;
}
