package com.banking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private Integer accountNumber;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private Double balance;

  @Column(nullable = false)
  private String accountType; // SAVINGS or CURRENT

  @Column(nullable = false)
  private Boolean locked = false;

  @Column(nullable = false)
  private Integer wrongAttempts = 0;

  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Transaction> transactions = new ArrayList<>();

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }

  public void addTransaction(Transaction transaction) {
    transactions.add(transaction);
    transaction.setAccount(this);
  }
}
