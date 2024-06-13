package com.ofg.qa.controller;

import com.ofg.qa.entity.User;
import com.ofg.qa.service.UserService;
import com.ofg.qa.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ApiResponse<List<User>> getUsers() {
        return new ApiResponse<>(true, "Data fetched successfully", userService.getUsers());
    }

    @GetMapping("/{id}")
    public ApiResponse<User> getUserById(@PathVariable long id) {
        return new ApiResponse<>(true, "Data fetched successfully", userService.getUserById(id));
    }

    @PostMapping
    public ApiResponse<User> createUser(@RequestBody User user) {
        return new ApiResponse<>(true, "Data added successfully", userService.saveUser(user));
    }

    @PutMapping("/{id}")
    public ApiResponse<User> updateUser(@PathVariable long id, @RequestBody User user) {
        User dbUser = userService.updateUser(id, user);
        if (dbUser != null) {
            return new ApiResponse<>(true, "Data updated successfully", dbUser);

        } else {
            return new ApiResponse<>(false, "User not found", null);
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Long> deleteUser(@PathVariable long id) {
        userService.deleteUserById(id);
        return new ApiResponse<>(true, "Data deleted successfully", id);
    }
}
