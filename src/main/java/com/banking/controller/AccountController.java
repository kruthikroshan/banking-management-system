package com.banking.controller;

import com.banking.dto.*;
import com.banking.entity.Transaction;
import com.banking.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AccountController {

  private final AccountService accountService;

  @PostMapping("/create")
  public ResponseEntity<?> createAccount(@Valid @RequestBody CreateAccountRequest request) {
    try {
      AccountDTO account = accountService.createAccount(request);
      return ResponseEntity.status(HttpStatus.CREATED).body(account);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
  }

  @GetMapping("/{accountNumber}")
  public ResponseEntity<?> getAccount(@PathVariable Integer accountNumber) {
    try {
      AccountDTO account = accountService.getAccount(accountNumber);
      return ResponseEntity.ok(account);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
    }
  }

  @GetMapping
  public ResponseEntity<List<AccountDTO>> getAllAccounts() {
    List<AccountDTO> accounts = accountService.getAllAccounts();
    return ResponseEntity.ok(accounts);
  }

  @PostMapping("/deposit")
  public ResponseEntity<?> deposit(@Valid @RequestBody TransactionRequest request) {
    try {
      AccountDTO account = accountService.deposit(request);
      return ResponseEntity.ok(account);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
  }

  @PostMapping("/withdraw")
  public ResponseEntity<?> withdraw(@Valid @RequestBody TransactionRequest request) {
    try {
      AccountDTO account = accountService.withdraw(request);
      return ResponseEntity.ok(account);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
  }

  @PostMapping("/transfer")
  public ResponseEntity<?> transfer(@Valid @RequestBody TransactionRequest request) {
    try {
      accountService.transfer(request);
      return ResponseEntity.ok(Map.of("message", "Transfer successful"));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
  }

  @PostMapping("/{accountNumber}/interest")
  public ResponseEntity<?> applyInterest(@PathVariable Integer accountNumber) {
    try {
      AccountDTO account = accountService.applyInterest(accountNumber);
      return ResponseEntity.ok(account);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
  }

  @GetMapping("/{accountNumber}/transactions")
  public ResponseEntity<?> getTransactions(@PathVariable Integer accountNumber) {
    try {
      List<Transaction> transactions = accountService.getTransactions(accountNumber);
      return ResponseEntity.ok(transactions);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
    }
  }

  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticate(@RequestBody Map<String, Object> credentials) {
    try {
      Integer accountNumber = (Integer) credentials.get("accountNumber");
      String password = (String) credentials.get("password");
      boolean authenticated = accountService.authenticate(accountNumber, password);
      return ResponseEntity.ok(Map.of("authenticated", authenticated));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
    }
  }

  @PostMapping("/{accountNumber}/change-password")
  public ResponseEntity<?> changePassword(@PathVariable Integer accountNumber,
      @RequestBody Map<String, String> passwords) {
    try {
      String oldPassword = passwords.get("oldPassword");
      String newPassword = passwords.get("newPassword");
      accountService.changePassword(accountNumber, oldPassword, newPassword);
      return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
  }

  @GetMapping("/health")
  public ResponseEntity<Map<String, String>> health() {
    Map<String, String> health = new HashMap<>();
    health.put("status", "UP");
    health.put("service", "Banking Management System");
    return ResponseEntity.ok(health);
  }
}
