package com.usefindar.app.configuration;

import com.usefindar.app.repository.users.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SetupConfigurations implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SetupConfigurations(UserRepository userRepository,
                               BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //create config
    }
}
