package com.example.spring_boot.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter

public class SignUp {


    @Id
    @Column
    private int id;

    @Column
    /* 配列要素重複チェック */
    private String account;

    private String password;

    @Column
    private String confirmPassword;

    @Column
    private String name;

    @Column(name = "branch_id", insertable = true, updatable = true)
    private Integer branchId;

    @Column(name = "department_id", insertable = true, updatable = true)
    private Integer departmentId;

    @Column(name = "is_stopped", insertable = false, updatable = true)
    private Integer isStopped;

    @Column(name = "created_date", insertable = false, updatable = false)
    private Date createdDate;

    @Column(name = "updated_date", insertable = false, updatable = true)
    private Date updatedDate;
}
