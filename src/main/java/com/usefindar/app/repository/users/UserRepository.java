package com.usefindar.app.repository.users;


import com.usefindar.app.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long Id);
    Optional<User> findUserByEmail(String email);
}
