package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserRecordDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public List<UserModel> getAllUsers() {
        return userService.findAll();
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
                           @JsonView(UserRecordDto.UserView.UserPut.class) UserRecordDto userRecordDto) {
        return userService.updateUser(userRecordDto, userId);
    }

    @PutMapping("/{userId}/password")
    @ResponseStatus(code = HttpStatus.OK)
    public String updatePassword(@PathVariable(value = "userId") UUID userId,
                                @RequestBody
                                @JsonView(UserRecordDto.UserView.PasswordPut.class) UserRecordDto userRecordDto) {
        return userService.updatePassword(userRecordDto, userId);
    }

    @PutMapping("/{userId}/image")
    @ResponseStatus(code = HttpStatus.OK)
    public String updateImage(@PathVariable(value = "userId") UUID userId,
                                 @RequestBody
                                 @JsonView(UserRecordDto.UserView.ImagePut.class) UserRecordDto userRecordDto) {
        return userService.updateImage(userRecordDto, userId);
    }
}
