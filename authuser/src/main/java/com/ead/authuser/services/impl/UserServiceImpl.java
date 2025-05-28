package com.ead.authuser.services.impl;

import com.ead.authuser.dtos.UserRecordDto;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.exceptions.ConstranintViolationException;
import com.ead.authuser.exceptions.NotFoundException;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserModel findById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Error: User not found!"));
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
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return userRepository.save(userModel);
    }

    private void validateInput(UserRecordDto userRecordDto) {
        if(alreadyExistsUsername(userRecordDto.username())){
            throw new ConstranintViolationException("Error: Username already exists!");
        }
        if(alreadyExistsEmail(userRecordDto.email())){
            throw new ConstranintViolationException("Error: Email already exists!");
        }
    }

    private boolean alreadyExistsEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private boolean alreadyExistsUsername(String username) {
        return userRepository.existsByUsername(username);
    }

}
