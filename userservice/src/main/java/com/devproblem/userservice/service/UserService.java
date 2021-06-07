package com.devproblem.userservice.service;

import com.devproblem.userservice.model.User;
import com.devproblem.userservice.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private IUserRepository repository;

    @Autowired
    public UserService(IUserRepository repository) {
        this.repository = repository;
    }

    public User create(User user) throws Exception {

        if (!isDuplicateUser(user)) {
            return repository.save(user);
        } else {
            throw new Exception("Duplicate User details: ID Number or Phone Number ");
        }
    }

    public User update(User user) throws Exception {
        return repository.save(user);

    }

    public User searchUser(String searchValue) throws Exception {
        Optional<User> user = repository.findAll().stream().filter(u -> u.getFirstName().equals(searchValue) ||
                u.getIdNumber().equals(searchValue) || u.getPhoneNumber().equals(searchValue))
                .findFirst();

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new Exception("User not found ");
        }
    }

    private boolean isDuplicateUser(User user) {
        return repository.findAll().stream().anyMatch(u -> u.getIdNumber().equals(user.getIdNumber()) ||
                u.getPhoneNumber().equals(user.getPhoneNumber()));
    }
}
