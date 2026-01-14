package com.banking.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

  @NotNull(message = "Account number is required")
  private Integer accountNumber;

  @NotNull(message = "Amount is required")
  @Positive(message = "Amount must be positive")
  private Double amount;

  private String password;

  // For transfer
  private Integer targetAccountNumber;
}
