package com.example.spring_boot.controller.form;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "・メッセージを入力してください")
    @Size(max = 500, message = "・タスクは500文字以内で入力してください")
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
