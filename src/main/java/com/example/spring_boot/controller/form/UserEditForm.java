package com.example.spring_boot.controller.form;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class UserEditForm {

    @Id
    @Column
    private int id;

    @Column
    @NotBlank(message = "アカウントを入力してください")
    /* 配列要素重複チェック */
    @Size(min = 6,max = 20, message = "アカウントは半角英数字かつ6文字以上20文字以下で入力してください")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "アカウントは半角英数字かつ6文字以上20文字以下で入力してください")
    private String account;

    @NotBlank(message = "パスワードを入力してください")
    @Pattern(regexp = "[a-zA-Z0-9]+.*", message = "パスワードは半角文字かつ6文字以上20文字以下で入力してください")
    @Size(min = 6,max = 20, message = "パスワードは半角文字かつ6文字以上20文字以下で入力してください")
    private String password;

    @Column
    private String confirmPassword;

    @NotBlank(message = "氏名を入力してください")
    @Size(max = 10, message = "氏名は10文字以下で入力してください")
    private String name;

    @NotNull(message = "支社を選択してください")
    @Column(name = "branch_id", insertable = true, updatable = true)
    private Integer branchId;

    @NotNull(message = "部署を選択してください")
    @Column(name = "department_id", insertable = true, updatable = true)
    private Integer departmentId;

    @Column(name = "is_stopped", insertable = false, updatable = true)
    private Integer isStopped;

    @Column(name = "created_date", insertable = false, updatable = false)
    private Date createdDate;

    @Column(name = "updated_date", insertable = false, updatable = true)
    private Date updatedDate;

}
