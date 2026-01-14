package com.banking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/")
  public String home(Model model) {
    model.addAttribute("appName", "Banking Management System");
    return "home";
  }
}
