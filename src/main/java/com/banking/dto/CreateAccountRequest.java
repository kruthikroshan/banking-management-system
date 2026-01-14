package com.banking.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {

  @NotNull(message = "Account number is required")
  @Min(value = 1000, message = "Account number must be at least 4 digits")
  private Integer accountNumber;

  @NotBlank(message = "Password is required")
  @Size(min = 4, message = "Password must be at least 4 characters")
  private String password;

  @NotNull(message = "Initial balance is required")
  @Min(value = 0, message = "Initial balance cannot be negative")
  private Double initialBalance;

  @NotBlank(message = "Account type is required")
  @Pattern(regexp = "SAVINGS|CURRENT", message = "Account type must be SAVINGS or CURRENT")
  private String accountType;
}
