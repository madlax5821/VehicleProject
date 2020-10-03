package com.ascending.controller;

import com.ascending.dao.UserDao;
import com.ascending.model.Role;
import com.ascending.model.User;
import com.ascending.repository.UserDaoImpl;
import com.ascending.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "User")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "getUserByNameAndEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserByNameAndEmail(@RequestParam("name") String name, @RequestParam("email") String email) {
        return userService.getUserByNameAndEmail(name, email);
    }

    @PostMapping(value = "saveUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public User saveUser(@RequestBody User user) {
        user = userService.save(user);
        return user;
    }
}
