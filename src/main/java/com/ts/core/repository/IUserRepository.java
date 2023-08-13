package com.ts.core.repository;

import com.ts.core.entities.IEntity;
import com.ts.core.entities.IUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface IUserRepository<T extends IUser>  {
    Optional<T> getUserByEmail(String email);
}
