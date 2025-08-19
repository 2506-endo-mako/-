package com.example.spring_boot.repository.entity;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "messages")
@Getter
@Setter

public class Message implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

//    @OneToMany(mappedBy="messages", cascade=CascadeType.ALL)
//    private List<User> users;

    // バージョン管理用フィールドを追加
//    @Version
//    private int version;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "category")
    private String category;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "created_date", insertable = false, updatable = false)
    private Date createdDate;

    @Column(name = "updated_date", insertable = false, updatable = true)
    private Date updatedDate;
}
