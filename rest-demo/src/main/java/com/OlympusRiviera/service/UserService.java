package com.OlympusRiviera.service;

import com.OlympusRiviera.model.User.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public String createUser(User user);
    public String updateUser(User user);
    public String deleteUser(String user);
    public User getUser(String googleId);
    public List<User> getAllUsers();
    public Optional<User> findUserByGoogleId(String googleId);
}
