package com.example.spring_boot.controller.form;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class UserForm {

    private int id;

    @Column
    @NotBlank(message = "アカウントを入力してください")
    private String account;

    @NotBlank(message = "パスワードを入力してください")
    private String password;

    private String name;

    @Column(name = "branch_id", insertable = true, updatable = true)
    private Integer branchId;

    @Column(name = "department_id", insertable = true, updatable = true)
    private Integer departmentId;

    @Column(name = "is_stopped")
    private Integer isStopped;

    @Column(name = "created_date", insertable = false, updatable = false)
    private Date createdDate;

    @Column(name = "updated_date", insertable = false, updatable = true)
    private Date updatedDate;

}
