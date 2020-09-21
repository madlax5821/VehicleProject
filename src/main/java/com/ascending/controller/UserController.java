package com.ascending.controller;

import com.ascending.model.User;
import com.ascending.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "User")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "getUserByNameAndEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserByNameAndEmail(@RequestParam("name")String name,@RequestParam("email")String email){
        return userService.getUserByNameAndEmail(name,email);
    }
}
