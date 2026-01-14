package com.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankingManagementSystemApplication {

  public static void main(String[] args) {
    SpringApplication.run(BankingManagementSystemApplication.class, args);
    System.out.println("\n==============================================");
    System.out.println("🏦 Banking Management System Started!");
    System.out.println("==============================================");
    System.out.println("📍 Server: http://localhost:8080");
    System.out.println("📊 H2 Console: http://localhost:8080/h2-console");
    System.out.println("📖 API Docs: http://localhost:8080/api/accounts");
    System.out.println("==============================================\n");
  }
}
