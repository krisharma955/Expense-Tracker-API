package com.K955.Expense_Tracker_API.DTOs.Auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignupRequest(

        @NotBlank
        String name,

        @Email
        @NotNull
        String email,

        @NotBlank
        String password
) {
}
