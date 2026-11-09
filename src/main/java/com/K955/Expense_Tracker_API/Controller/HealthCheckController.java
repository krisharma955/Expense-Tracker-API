package com.K955.Expense_Tracker_API.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/health-check")
    public String healthCheck() {
        return "Lando Norris P1!";
    }

}
