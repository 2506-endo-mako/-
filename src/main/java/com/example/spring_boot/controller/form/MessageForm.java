package com.example.spring_boot.controller.form;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter

public class MessageForm {

        @Column
        private int id;

        @NotBlank(message = "件名を入力してください")
        @Pattern(regexp = "[^\\u3000]*", message = "件名を入力してください")
        @Size(max = 30, message = "件名は30文字以内で入力してください")
        @Column
        private String title;

        @NotBlank(message = "本文を入力してください")
        @Pattern(regexp = "[^\\u3000]*", message = "本文を入力してください")
        @Size(max = 1000, message = "本文は1000文字以内で入力してください")
        @Column
        private String text;

        @NotBlank(message = "カテゴリを入力してください")
        @Pattern(regexp = "[^\\u3000]*", message = "カテゴリを入力してください")
        @Size(max = 10, message = "カテゴリは10文字以内で入力してください")
        @Column
        private String category;

        @Column(name = "user_id")
        private int userId;

        @Column(name = "created_date")
        private Date createdDate;

        @Column(name = "updated_date")
        private Date updatedDate;
}
