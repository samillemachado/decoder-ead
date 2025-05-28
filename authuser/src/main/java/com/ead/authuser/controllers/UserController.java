package com.ead.authuser.controllers;

import com.ead.authuser.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }
}
