package com.K955.Expense_Tracker_API.Controller;

import com.K955.Expense_Tracker_API.DTOs.User.UpdateUserRequest;
import com.K955.Expense_Tracker_API.DTOs.User.UserProfileResponse;
import com.K955.Expense_Tracker_API.Security.JwtAuthUtil;
import com.K955.Expense_Tracker_API.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtAuthUtil jwtAuthUtil;

    @GetMapping("/me")
    @Operation(summary = "Get User Profile")
    public ResponseEntity<UserProfileResponse> getUserProfile() {
        Long userId = jwtAuthUtil.getCurrentUserId();
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    @PatchMapping
    @Operation(summary = "Update User Profile")
    public ResponseEntity<UserProfileResponse> updateUserProfile(@Valid @RequestBody UpdateUserRequest request) {
        Long userId = jwtAuthUtil.getCurrentUserId();
        return ResponseEntity.ok(userService.updateUserProfile(userId, request));
    }

    @DeleteMapping
    @Operation(summary = "Delete User Profile")
    public ResponseEntity<Void> deleteUserProfile() {
        Long userId = jwtAuthUtil.getCurrentUserId();
        userService.deleteUserProfile(userId);
        return ResponseEntity.noContent().build();
    }

}
