package com.banking.service;

import com.banking.dto.*;
import com.banking.entity.Account;
import com.banking.entity.Transaction;
import com.banking.repository.AccountRepository;
import com.banking.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;
  private final TransactionRepository transactionRepository;

  private static final double SAVINGS_MIN_BALANCE = 500.0;
  private static final double SAVINGS_WITHDRAW_LIMIT = 10000.0;
  private static final double YEARLY_INTEREST = 4.0;

  @Transactional
  public AccountDTO createAccount(CreateAccountRequest request) {
    if (accountRepository.existsByAccountNumber(request.getAccountNumber())) {
      throw new RuntimeException("Account number already exists");
    }

    Account account = new Account();
    account.setAccountNumber(request.getAccountNumber());
    account.setPassword(request.getPassword());
    account.setBalance(request.getInitialBalance());
    account.setAccountType(request.getAccountType());
    account.setLocked(false);
    account.setWrongAttempts(0);

    account = accountRepository.save(account);

    Transaction transaction = new Transaction("INITIAL_DEPOSIT", request.getInitialBalance(),
        request.getInitialBalance(), "Account created with initial deposit");
    account.addTransaction(transaction);
    transactionRepository.save(transaction);

    return convertToDTO(account);
  }

  public AccountDTO getAccount(Integer accountNumber) {
    Account account = accountRepository.findByAccountNumber(accountNumber)
        .orElseThrow(() -> new RuntimeException("Account not found"));
    return convertToDTO(account);
  }

  public List<AccountDTO> getAllAccounts() {
    return accountRepository.findAll().stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Transactional
  public AccountDTO deposit(TransactionRequest request) {
    Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
        .orElseThrow(() -> new RuntimeException("Account not found"));

    if (request.getAmount() <= 0) {
      throw new RuntimeException("Invalid deposit amount");
    }

    account.setBalance(account.getBalance() + request.getAmount());

    Transaction transaction = new Transaction("DEPOSIT", request.getAmount(),
        account.getBalance(), "Deposit made");
    account.addTransaction(transaction);

    transactionRepository.save(transaction);
    accountRepository.save(account);

    return convertToDTO(account);
  }

  @Transactional
  public AccountDTO withdraw(TransactionRequest request) {
    Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
        .orElseThrow(() -> new RuntimeException("Account not found"));

    if (account.getLocked()) {
      throw new RuntimeException("Account is locked");
    }

    if (request.getAmount() <= 0) {
      throw new RuntimeException("Invalid withdrawal amount");
    }

    if ("SAVINGS".equals(account.getAccountType())) {
      if (request.getAmount() > SAVINGS_WITHDRAW_LIMIT) {
        throw new RuntimeException("Withdrawal limit exceeded (Max ₹" + SAVINGS_WITHDRAW_LIMIT + ")");
      }
      if (account.getBalance() - request.getAmount() < SAVINGS_MIN_BALANCE) {
        throw new RuntimeException("Cannot withdraw! Minimum balance ₹" + SAVINGS_MIN_BALANCE + " must be maintained");
      }
    }

    if (request.getAmount() > account.getBalance()) {
      throw new RuntimeException("Insufficient balance");
    }

    account.setBalance(account.getBalance() - request.getAmount());

    Transaction transaction = new Transaction("WITHDRAW", request.getAmount(),
        account.getBalance(), "Withdrawal made");
    account.addTransaction(transaction);

    transactionRepository.save(transaction);
    accountRepository.save(account);

    return convertToDTO(account);
  }

  @Transactional
  public void transfer(TransactionRequest request) {
    Account sourceAccount = accountRepository.findByAccountNumber(request.getAccountNumber())
        .orElseThrow(() -> new RuntimeException("Source account not found"));

    Account targetAccount = accountRepository.findByAccountNumber(request.getTargetAccountNumber())
        .orElseThrow(() -> new RuntimeException("Target account not found"));

    if (sourceAccount.getLocked()) {
      throw new RuntimeException("Source account is locked");
    }

    if (request.getAmount() <= 0) {
      throw new RuntimeException("Invalid transfer amount");
    }

    if (request.getAmount() > sourceAccount.getBalance()) {
      throw new RuntimeException("Insufficient balance");
    }

    // Debit from source
    sourceAccount.setBalance(sourceAccount.getBalance() - request.getAmount());
    Transaction debitTransaction = new Transaction("TRANSFER_OUT", request.getAmount(),
        sourceAccount.getBalance(), "Transfer to account #" + targetAccount.getAccountNumber());
    sourceAccount.addTransaction(debitTransaction);

    // Credit to target
    targetAccount.setBalance(targetAccount.getBalance() + request.getAmount());
    Transaction creditTransaction = new Transaction("TRANSFER_IN", request.getAmount(),
        targetAccount.getBalance(), "Transfer from account #" + sourceAccount.getAccountNumber());
    targetAccount.addTransaction(creditTransaction);

    transactionRepository.save(debitTransaction);
    transactionRepository.save(creditTransaction);
    accountRepository.save(sourceAccount);
    accountRepository.save(targetAccount);
  }

  @Transactional
  public AccountDTO applyInterest(Integer accountNumber) {
    Account account = accountRepository.findByAccountNumber(accountNumber)
        .orElseThrow(() -> new RuntimeException("Account not found"));

    if (!"SAVINGS".equals(account.getAccountType())) {
      throw new RuntimeException("Interest only available for Savings Accounts");
    }

    double monthlyRate = YEARLY_INTEREST / 12;
    double interest = (account.getBalance() * monthlyRate) / 100;

    account.setBalance(account.getBalance() + interest);

    Transaction transaction = new Transaction("INTEREST", interest,
        account.getBalance(), "Monthly interest applied");
    account.addTransaction(transaction);

    transactionRepository.save(transaction);
    accountRepository.save(account);

    return convertToDTO(account);
  }

  public List<Transaction> getTransactions(Integer accountNumber) {
    Account account = accountRepository.findByAccountNumber(accountNumber)
        .orElseThrow(() -> new RuntimeException("Account not found"));
    return transactionRepository.findByAccountIdOrderByTimestampDesc(account.getId());
  }

  @Transactional
  public void changePassword(Integer accountNumber, String oldPassword, String newPassword) {
    Account account = accountRepository.findByAccountNumber(accountNumber)
        .orElseThrow(() -> new RuntimeException("Account not found"));

    if (!account.getPassword().equals(oldPassword)) {
      throw new RuntimeException("Incorrect old password");
    }

    if (newPassword == null || newPassword.length() < 4) {
      throw new RuntimeException("New password must be at least 4 characters");
    }

    account.setPassword(newPassword);
    accountRepository.save(account);
  }

  public boolean authenticate(Integer accountNumber, String password) {
    Account account = accountRepository.findByAccountNumber(accountNumber)
        .orElseThrow(() -> new RuntimeException("Account not found"));

    if (account.getLocked()) {
      throw new RuntimeException("Account is locked");
    }

    if (account.getPassword().equals(password)) {
      account.setWrongAttempts(0);
      accountRepository.save(account);
      return true;
    } else {
      account.setWrongAttempts(account.getWrongAttempts() + 1);
      if (account.getWrongAttempts() >= 3) {
        account.setLocked(true);
        accountRepository.save(account);
        throw new RuntimeException("Account locked due to 3 wrong attempts");
      }
      accountRepository.save(account);
      throw new RuntimeException("Incorrect password. Attempts left: " + (3 - account.getWrongAttempts()));
    }
  }

  private AccountDTO convertToDTO(Account account) {
    return new AccountDTO(
        account.getId(),
        account.getAccountNumber(),
        account.getBalance(),
        account.getAccountType(),
        account.getLocked());
  }
}
