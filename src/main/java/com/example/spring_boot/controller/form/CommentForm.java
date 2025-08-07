package com.example.spring_boot.controller.form;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Getter
@Setter

public class CommentForm {
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
