package com.K955.Expense_Tracker_API.DTOs.Auth;

import com.K955.Expense_Tracker_API.DTOs.User.UserProfileResponse;

public record AuthResponse(
        String token,
        UserProfileResponse user
) {
}
