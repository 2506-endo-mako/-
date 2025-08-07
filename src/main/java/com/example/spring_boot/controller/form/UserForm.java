package com.example.spring_boot.controller.form;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserForm {
    @Column
    private int id;

    @Column
    private String account;

    @Column
    private String password;

    @Column
    private String name;

    @Column(name = "branch_id")
    private int branchId;

    @Column(name = "department_id")
    private int departmentId;

    @Column(name = "is_stopped")
    private int isStopped;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

}
