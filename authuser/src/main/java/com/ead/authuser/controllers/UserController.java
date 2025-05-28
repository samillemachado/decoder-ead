package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserRecordDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Page<UserModel> getAllUsers(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(code = HttpStatus.OK)
    public UserModel getOneUser(@PathVariable(value = "userId") UUID userId) {
        return userService.findById(userId);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(value = "userId") UUID userId) {
        userService.delete(userId);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(code = HttpStatus.OK)
    public UserModel updateUser(@PathVariable(value = "userId") UUID userId,
                                @RequestBody
                                @Validated(UserRecordDto.UserView.UserPut.class)
                                @JsonView(UserRecordDto.UserView.UserPut.class) UserRecordDto userRecordDto) {
        return userService.updateUser(userRecordDto, userId);
    }

    @PutMapping("/{userId}/password")
    @ResponseStatus(code = HttpStatus.OK)
    public String updatePassword(@PathVariable(value = "userId") UUID userId,
                                 @RequestBody
                                 @Validated(UserRecordDto.UserView.PasswordPut.class)
                                 @JsonView(UserRecordDto.UserView.PasswordPut.class) UserRecordDto userRecordDto) {
        return userService.updatePassword(userRecordDto, userId);
    }

    @PutMapping("/{userId}/image")
    @ResponseStatus(code = HttpStatus.OK)
    public String updateImage(@PathVariable(value = "userId") UUID userId,
                              @RequestBody
                              @Validated(UserRecordDto.UserView.ImagePut.class)
                              @JsonView(UserRecordDto.UserView.ImagePut.class) UserRecordDto userRecordDto) {
        return userService.updateImage(userRecordDto, userId);
    }
}
