package com.ts.core.entities;

import jakarta.persistence.PreUpdate;

import java.util.Calendar;

public class EntityUpdateListener {

    @PreUpdate
    public void beforeUpdate(IEntity entity) {
        // Update the updatedAt field with the current date and time before the entity is updated.
        entity.setUpdatedAt(Calendar.getInstance().getTime());
    }
}