package com.example.spring_boot.repository.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Entity
@Table(name = "Comments")
@Getter
@Setter
public class Comment {
    @Id
    @Column
    private int id;

    @Column
    private String text;

    @Column
    private int user_id;

    @Column
    private int message_id;

    @Column
    private Date created_date;

    @Column
    private Date updated_date;
}
