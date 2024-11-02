package com.OlympusRiviera.service.impl;

import com.OlympusRiviera.model.User;
import com.OlympusRiviera.repository.UserRepository;
import com.OlympusRiviera.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceimpl implements UserService {

    UserRepository userRepository;

    public UserServiceimpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String createUser(User user) {
        userRepository.save(user);
        return "Success";
    }

    @Override
    public String updateUser(User cloudVentor) {
//        more logic
        userRepository.save(cloudVentor);
        return "Updated sUCCESS";
    }

    @Override
    public String deleteUser(String cloudVentorId) {
        userRepository.deleteById(cloudVentorId);
        return "Deleted SSucces";
    }

    @Override
    public User getUser(String cloudVendorId) {

        return userRepository.findById(cloudVendorId).get();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
