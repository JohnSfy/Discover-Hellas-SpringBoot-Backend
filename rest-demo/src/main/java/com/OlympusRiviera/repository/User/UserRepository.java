package com.OlympusRiviera.repository.User;

import com.OlympusRiviera.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findByGoogleId(String googleId);

}
