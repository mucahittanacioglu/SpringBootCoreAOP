package com.ts.core.entities;

import com.ts.core.security.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public abstract class IUser extends IEntity {
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {

        this.roles.add(role);
    }

}
