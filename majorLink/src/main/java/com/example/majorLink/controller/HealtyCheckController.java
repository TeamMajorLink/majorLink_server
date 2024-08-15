package com.example.majorLink.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealtyCheckController {

    @GetMapping("/health")
    public String checkHealth() {
        return "im healty";
    }
}
