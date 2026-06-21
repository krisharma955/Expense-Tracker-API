package com.K955.Expense_Tracker_API.Service.ImpL;

import com.K955.Expense_Tracker_API.DTOs.User.UpdateUserRequest;
import com.K955.Expense_Tracker_API.DTOs.User.UserProfileResponse;
import com.K955.Expense_Tracker_API.Entity.User;
import com.K955.Expense_Tracker_API.Exception.ResourceNotFoundException;
import com.K955.Expense_Tracker_API.Mapper.UserMapper;
import com.K955.Expense_Tracker_API.Repository.UserRepository;
import com.K955.Expense_Tracker_API.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UserServiceImpL implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserProfileResponse getUserProfile(Long userId) {
        User user = getUser(userId);
        return userMapper.toUserProfileResponse(user);
    }

    @Override
    public UserProfileResponse updateUserProfile(Long userId, UpdateUserRequest request) {
        User user = getUser(userId);
        if(request.name() != null) user.setName(request.name());
        User saved = userRepository.save(user);
        return userMapper.toUserProfileResponse(user);
    }

    @Override
    public void deleteUserProfile(Long userId) {
        User user = getUser(userId);
        user.setDeletedAt(Instant.now());
        userRepository.save(user);
    }

    /// Util Methods

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(userId.toString(), "User"));
    }

}
