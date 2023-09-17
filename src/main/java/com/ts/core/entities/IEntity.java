package com.ts.core.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@MappedSuperclass @Data
@EntityListeners(EntityUpdateListener.class)
public abstract class IEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Date createdAt= Calendar.getInstance().getTime();
    @Column
    private Date updatedAt=  Calendar.getInstance().getTime();
}