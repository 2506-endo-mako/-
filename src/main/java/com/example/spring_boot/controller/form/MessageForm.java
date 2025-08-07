package com.example.spring_boot.controller.form;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter

public class MessageForm {

        @Column
        private int id;

        @Column
        private String title;

        @Column
        private String text;

        @Column
        private String category;

        @Column(name = "user_id")
        private int userId;

        @Column(name = "created_date")
        private Date createdDate;

        @Column(name = "updated_date")
        private Date updatedDate;
}
