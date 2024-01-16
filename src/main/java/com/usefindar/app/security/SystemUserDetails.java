package com.usefindar.app.security;

import com.usefindar.app.repository.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * MyUserDetails
 */
@Service
public class SystemUserDetails implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    var user = userRepository.findUserByEmail(email).orElseThrow(() -> new  UsernameNotFoundException("User with '" + email + "' not found"));
    return new ManageSystemUser(email, user.getPassword());
  }

}