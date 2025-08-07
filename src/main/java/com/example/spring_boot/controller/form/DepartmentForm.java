package com.example.spring_boot.controller.form;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter

public class DepartmentForm {

    @Column
    private int id;

    @Column
    private String name;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;
}
