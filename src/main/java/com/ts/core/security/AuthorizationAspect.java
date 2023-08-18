package com.ts.core.security;

import com.ts.core.entities.IUser;
import com.ts.core.repository.IUserRepository;
import com.ts.core.exceptions.UnauthenticatedException;
import com.ts.core.exceptions.UnauthorizedException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

@Aspect
@Order(2)
@Component
public class AuthorizationAspect {

    @Autowired
    IUserRepository userRepository;

    @Before("@annotation(requiredRoles)")
    public void authorize(JoinPoint joinPoint, RequiredRoles requiredRoles) throws Throwable{

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            // Implement your logic to fetch user roles from the database or wherever they are stored
            Set<Role> userRoles = getUserRoles(authentication.getName());
            //authentication.getPrincipal();
            // Check if the user has at least one of the required roles
            boolean hasRequiredRole = Arrays.stream(requiredRoles.value())
                    .anyMatch(userRoles::contains);

            if (!hasRequiredRole) {
                throw new UnauthorizedException("Unauthorized");
            }
        } else {
            throw new UnauthenticatedException("Unauthenticated");
        }
    }

    private Set<Role> getUserRoles(String email) throws Throwable {
        // Implement your logic to fetch user roles from the database or any other source
        // Return a Set<Role> containing the user's roles
        IUser usr =(IUser) userRepository.getUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return usr.getRoles();
    }


}