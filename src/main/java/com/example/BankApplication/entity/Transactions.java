package com.example.BankApplication.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Table(name="transactions")
@Data
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fromAccount;
    private String toAccount;
    private double amount;
    private Date time;
    private String status;
    public Transactions() {
    }
}
