package com.example.supermarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

public class dashboard {


    @Controller
public class HomeController {
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";  // Or Thymeleaf template
    }
}
}
