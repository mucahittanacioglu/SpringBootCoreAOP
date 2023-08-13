package com.ts.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

//TODO roller confiure edilebilir yapıda olsun
@Data @AllArgsConstructor
public class Role extends IEntity{
    private String name;

    @Override
    public String toString() {
        return this.name;
    }
}
