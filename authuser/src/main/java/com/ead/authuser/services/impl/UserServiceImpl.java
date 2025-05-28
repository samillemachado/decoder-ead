package com.ead.authuser.services.impl;

import com.ead.authuser.dtos.UserRecordDto;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.exceptions.ConstranintViolationException;
import com.ead.authuser.exceptions.InvalidCurrentPasswordException;
import com.ead.authuser.exceptions.NotFoundException;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;
    final String ERROR_USER_NOT_FOUND = "User not found";
    final String ZONE_ID_UTC = "UTC";
    final String INVALID_CURRENT_PASSWORD = "Invalid Current Password.";
    final String USERNAME_ALREADY_EXISTS = "Username already exists!";
    final String EMAIL_ALREADY_EXISTS = "Email already exists!";
    final String PASSWORD_UPDATED_SUCCESSFULLY = "Password updated successfully!";
    final String IMAGE_UPDATED_SUCCESSFULLY = "Image updated successfully!";

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

    @Override
    public UserModel findById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ERROR_USER_NOT_FOUND));
    }

    @Override
    public void delete(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserModel registerUser(UserRecordDto userRecordDto) {
        validateInput(userRecordDto);
        var userModel = new UserModel();
        BeanUtils.copyProperties(userRecordDto, userModel);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.USER);
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of(ZONE_ID_UTC)));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of(ZONE_ID_UTC)));
        return userRepository.save(userModel);
    }

    @Override
    public UserModel updateUser(UserRecordDto userRecordDto, UUID userId) {
        var userModel = findById(userId);
        userModel.setFullName(userRecordDto.fullName());
        userModel.setPhoneNumber(userRecordDto.phoneNumber());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of(ZONE_ID_UTC)));
        return userRepository.save(userModel);
    }

    @Override
    public String updatePassword(UserRecordDto userRecordDto, UUID userId) {
        var userModel = findById(userId);
        if(!validateCurrentPassword(userRecordDto, userModel)){
            throw new InvalidCurrentPasswordException(INVALID_CURRENT_PASSWORD);
        };
        userModel.setPassword(userRecordDto.password());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of(ZONE_ID_UTC)));
        userRepository.save(userModel);
        return PASSWORD_UPDATED_SUCCESSFULLY;
    }

    @Override
    public String updateImage(UserRecordDto userRecordDto, UUID userId) {
        var userModel = findById(userId);
        userModel.setImageUrl(userRecordDto.imageUrl());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of(ZONE_ID_UTC)));
        userRepository.save(userModel);
        return IMAGE_UPDATED_SUCCESSFULLY;
    }

    private boolean validateCurrentPassword(UserRecordDto userRecordDto, UserModel userModel) {
        return userModel.getPassword().equals(userRecordDto.oldPassword());
    }

    private void validateInput(UserRecordDto userRecordDto) {
        if(alreadyExistsUsername(userRecordDto.username())){
            throw new ConstranintViolationException(USERNAME_ALREADY_EXISTS);
        }
        if(alreadyExistsEmail(userRecordDto.email())){
            throw new ConstranintViolationException(EMAIL_ALREADY_EXISTS);
        }
    }

    private boolean alreadyExistsEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private boolean alreadyExistsUsername(String username) {
        return userRepository.existsByUsername(username);
    }

}
