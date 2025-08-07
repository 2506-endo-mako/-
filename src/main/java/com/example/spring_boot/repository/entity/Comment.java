package com.example.spring_boot.repository.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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

    @Column(name = "user_id")
    private int userId;

    @Column(name = "message_id")
    private int messageId;

    @Column(name = "created_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;
}
