package com.example.spring_boot.repository.entity;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter

public class User implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "account")
    private String account;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "department_id", insertable = true, updatable = false)
    private Integer departmentId;

    @Column(name = "branch_id", insertable = true, updatable = false)
    private Integer branchId;

    @Column(name = "is_stopped")
    private Integer isStopped;

    @Column(name = "created_date", insertable = false, updatable = false)
    private Date createdDate;

    @Column(name = "updated_date", insertable = false, updatable = true)
    private Date updatedDate;

    // ★リレーションシップはそのまま残します
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "department_id")
//    private Department department;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "branch_id")
//    private Branch branch;
}
