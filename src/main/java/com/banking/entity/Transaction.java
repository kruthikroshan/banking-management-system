package com.banking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String type; // DEPOSIT, WITHDRAW, TRANSFER, INTEREST

  @Column(nullable = false)
  private Double amount;

  @Column(nullable = false)
  private Double finalBalance;

  @Column(nullable = false)
  private LocalDateTime timestamp;

  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", nullable = false)
  @JsonIgnore
  private Account account;

  @PrePersist
  protected void onCreate() {
    timestamp = LocalDateTime.now();
  }

  public Transaction(String type, Double amount, Double finalBalance, String description) {
    this.type = type;
    this.amount = amount;
    this.finalBalance = finalBalance;
    this.description = description;
    this.timestamp = LocalDateTime.now();
  }
}
