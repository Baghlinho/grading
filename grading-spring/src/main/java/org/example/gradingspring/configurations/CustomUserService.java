package org.example.gradingspring.configurations;

import org.example.dto.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserService implements UserDetailsService{
    private final UserService userService;

    @Autowired
    public CustomUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.getUserByEmail(username);
        if(user == null) throw new UsernameNotFoundException(username);
        String role = user.getRole().toUpperCase();
        Collection<GrantedAuthority> authorities =
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role));
        return new org.springframework.security.core.userdetails
                .User(username, user.getPasswordHash(), authorities);
    }
}
