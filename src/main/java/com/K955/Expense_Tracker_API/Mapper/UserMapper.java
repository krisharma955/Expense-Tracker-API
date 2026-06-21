package com.K955.Expense_Tracker_API.Mapper;

import com.K955.Expense_Tracker_API.DTOs.User.UserProfileResponse;
import com.K955.Expense_Tracker_API.Entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserProfileResponse toUserProfileResponse(User user);

}
