package com.OlympusRiviera.service;

import com.OlympusRiviera.model.User.ProviderUser;
import com.OlympusRiviera.model.User.RegisteredUser;
import com.OlympusRiviera.model.User.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public String createUser(User user);

    public String updateUser(Optional<User> user);

    public String deleteUser(String user);

    public Optional<User> getUser(String user_id);

    public Optional<ProviderUser> getProviderUser(String id);

    public List<User> getAllUsers();

    public User findUserByGoogleId(String googleId);

//    public String createRegisteredUser(String user, String hobbies, String preferences);

//    public Optional<User> getRegisteredUser(String user_id);

    public String createRegisteredUser(RegisteredUser registeredUser);

    public String createProviderUser(ProviderUser providerUser);
}