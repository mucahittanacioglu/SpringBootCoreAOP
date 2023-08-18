package com.ts.core.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

//TODO roller configure edilebilir yapıda olsun
public enum Role {
    ADD,DELETE,UPDATE,READ
}
