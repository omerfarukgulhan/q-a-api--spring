package com.ofg.qa.service;

import com.ofg.qa.dao.UserRepository;
import com.ofg.qa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(long id, User user) {
        Optional<User> dbUser = userRepository.findById(id);
        if (dbUser.isPresent()) {
            User updatedUser = dbUser.get();
            updatedUser.setUsername(user.getUsername());
            updatedUser.setPassword(user.getPassword());
            userRepository.save(updatedUser);
            return updatedUser;
        } else
            return null;
    }

    public void deleteUserById(long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
