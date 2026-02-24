package com.example.BankApplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Entity
@Table(name="user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(unique=true)
    private String email;
    private String password;
    private String role; // user or admin
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Account> accounts;
    public User() {
    }
}
