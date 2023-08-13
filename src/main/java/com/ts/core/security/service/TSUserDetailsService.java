package com.ts.core.security.service;

import com.ts.core.repository.IUserRepository;
import com.ts.core.entities.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TSUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        IUser Iuser = null;
        try {
            Iuser = (IUser) userRepository.getUserByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        return new org.springframework.security.core.userdetails.User(
                Iuser.getEmail(), Iuser.getPassword(), new ArrayList<>()
        );
    }
}