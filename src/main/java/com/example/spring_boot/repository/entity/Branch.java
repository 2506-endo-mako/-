package com.example.spring_boot.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "branches")
@Getter
@Setter
public class Branch {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_date", insertable = false, updatable = false)
    private Date createdDate;

    @Column(name = "updated_date", insertable = false, updatable = true)
    private Date updatedDate;

//    @ManyToOne
//    @JoinColumn(name="id")
//    private User user;
}

