package com.K955.Expense_Tracker_API.Service;

import com.K955.Expense_Tracker_API.DTOs.User.UpdateUserRequest;
import com.K955.Expense_Tracker_API.DTOs.User.UserProfileResponse;
import jakarta.validation.Valid;

public interface UserService {
    UserProfileResponse getUserProfile(Long userId);

    UserProfileResponse updateUserProfile(Long userId, @Valid UpdateUserRequest request);

    void deleteUserProfile(Long userId);
}
