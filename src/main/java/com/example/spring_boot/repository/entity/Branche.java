package com.example.spring_boot.repository.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Entity
@Table(name = "Branches")
@Getter
@Setter
public class Branche {
    @Id
    @Column
    private int id;

    @Column
    private String name;

    @Column
    private Date created_date;

    @Column
    private Date updated_date;

}
