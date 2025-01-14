package com.OlympusRiviera.service.impl;

import com.OlympusRiviera.model.User.User;
import com.OlympusRiviera.repository.User.UserRepository;
import com.OlympusRiviera.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceimpl implements UserService {

    private final UserRepository userRepository;

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
        return "Updated Success";
    }

    @Override
    public String deleteUser(String cloudVentorId) {
        userRepository.deleteById(cloudVentorId);
        return "Deleted Success";
    }

    @Override
    public User getUser(String id) {

        return userRepository.findById(id).get();
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    public Optional<User> findUserByGoogleId(String googleId) {
        return userRepository.findByGoogleId(googleId);
    }
}
