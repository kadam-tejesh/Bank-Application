package com.example.BankApplication.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;


@Entity
@Table(name="account")
@Data //lombok used to automatically generate the getters and setters
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String accountNo;
    private BigDecimal balance;

    @Version
    private Long version;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;




    public Account() {
    }

}
