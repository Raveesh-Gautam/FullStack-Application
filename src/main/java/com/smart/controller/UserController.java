package com.smart.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.smart.model.User;
import com.smart.repository.UserRepository;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    public User addUser(@RequestBody User user) {
        userRepository.save(user);
        return user;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/test")
    public String testApi() {
        return "Hello from the test function";
    }

    @PutMapping("/user/{id}")
    public void updateUser(@RequestBody User user, @PathVariable Long id) {
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setEmail(user.getEmail());
            existingUser.setName(user.getName());
            existingUser.setPassword(user.getPassword());
            existingUser.setUsername(user.getUsername());
            userRepository.save(existingUser);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @DeleteMapping("user/{id}")
    public void DeleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

}

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User not found with ID: " + id);
    }
}
