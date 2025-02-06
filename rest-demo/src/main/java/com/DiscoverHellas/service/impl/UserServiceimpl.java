package com.DiscoverHellas.service.impl;

import com.DiscoverHellas.model.User.ProviderUser;
import com.DiscoverHellas.model.User.RegisteredUser;
import com.DiscoverHellas.model.User.User;
import com.DiscoverHellas.repository.User.UserRepository;
import com.DiscoverHellas.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
    public String updateUser(Optional<User> user) {
        if (user.isPresent()) {
            userRepository.save(user.get());
            return "Updated Success";
        } else {
            return "User not found";
        }
    }

    @Override
    public String deleteUser(String cloudVentorId) {
        userRepository.deleteById(cloudVentorId);
        return "Deleted Success";
    }

    @Override
    public Optional<User> getUser(String id) {

        return userRepository.findById(id);
    }

    public Optional<ProviderUser> getProviderUser(String id) {
        return userRepository.findById(id).map(user -> {
            if (user instanceof ProviderUser) {
                return (ProviderUser) user;
            } else {
                throw new IllegalArgumentException("User is not of type ProviderUser");
            }
        });
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserByGoogleId(String googleId) {
        return userRepository.findByGoogleId(googleId);
    }


//    public Optional<User> getRegisteredUser(String user_id){
//        return userRepository.findById(user_id);
//    }

    @Override
    public String createRegisteredUser(RegisteredUser user) {
        userRepository.save(user);
        return "Success";
    }

    @Override
    public String createProviderUser(ProviderUser user) {
        userRepository.save(user);
        return "Success";
    }


}
