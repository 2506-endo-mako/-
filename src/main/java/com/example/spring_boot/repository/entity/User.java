package com.example.spring_boot.repository.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Entity
@Table(name = "Users")
@Getter
@Setter

public class User {
    @Id
    @Column
    private int id;

    @Column
    private String title;

    @Column
    private String text;

    @Column
    private String category;

    @Column
    private int user_id;

    @Column
    private Date created_date;

    @Column
    private Date updated_date;
}
