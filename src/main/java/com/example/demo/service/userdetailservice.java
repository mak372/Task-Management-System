package com.example.demo.service;
import com.example.demo.entity.task;
import com.example.demo.repository.userrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class userdetailservice implements UserDetailsService {

    @Autowired
    private userrepository repo;
    
    @Autowired
    JWTService jwtservice;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        task user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}

