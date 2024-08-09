package com.example.skyWardWingss.mapper;

import com.example.skyWardWingss.dao.entity.User;
import com.example.skyWardWingss.model.dto.request.UserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRequest userRequest);
}
