package com.ead.authuser.services;

import com.ead.authuser.dtos.UserRecordDto;
import com.ead.authuser.models.UserModel;
import org.apache.catalina.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<UserModel> findAll();

    UserModel findById(UUID userId);

    void delete(UUID userId);

    UserModel registerUser(UserRecordDto userRecordDto);

    UserModel updateUser(UserRecordDto userRecordDto, UUID userId);

    String updatePassword(UserRecordDto userRecordDto, UUID userId);

    String updateImage(UserRecordDto userRecordDto, UUID userId);
}

