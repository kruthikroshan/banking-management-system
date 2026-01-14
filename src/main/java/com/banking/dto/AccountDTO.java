package com.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
  private Long id;
  private Integer accountNumber;
  private Double balance;
  private String accountType;
  private Boolean locked;
}
