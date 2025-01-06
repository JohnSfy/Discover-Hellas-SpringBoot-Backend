package com.OlympusRiviera.service;

import com.OlympusRiviera.model.User.User;

import java.util.List;

public interface UserService {
    public String createUser(User user);
    public String updateUser(User cloudVentor);
    public String deleteUser(String cloudVentorId);
    public User getUser(String cloudVendorId);
    public List<User> getAllUsers();
}
