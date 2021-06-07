package com.devproblem.userservice.controller;

import javax.validation.Valid;

import com.devproblem.userservice.model.User;
import com.devproblem.userservice.service.UserService;
import com.devproblem.userservice.util.UserServiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("user-api/v1")
public class UserController {

    private final UserService userService;
    private final UserServiceValidator validator;

    @Autowired
    public UserController(UserService userService, UserServiceValidator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody @Valid User user) {
        try {
            validator.validate(user);
            User createdUser = userService.create(user);
            return ResponseEntity.ok(createdUser);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Valid User updateUser) {
        try {

            User user = userService.searchUser(updateUser.getIdNumber());
            if (user.getIdNumber().equals(updateUser.getIdNumber())) {
                updateUser.setId(user.getId());
                User updated = userService.update(updateUser);
                return ResponseEntity.ok(updated);
            } else {
                return new ResponseEntity<>("Cannot update ID Number", HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> get(@RequestParam String search) {
        try {
            User found = userService.searchUser(search);
            return ResponseEntity.ok(found);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
