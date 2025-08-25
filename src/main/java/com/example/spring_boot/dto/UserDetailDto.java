package com.example.spring_boot.dto;
import lombok.AllArgsConstructor; // 全フィールドを引数に取るコンストラクタを生成
import lombok.Data;              // Getter, Setter, toString, equals, hashCodeを生成
import lombok.NoArgsConstructor;   // 引数なしのコンストラクタを生成

@Data // Getter, Setter, etc.
@NoArgsConstructor // 引数なしコンストラクタ
@AllArgsConstructor // 全引数コンストラクタ
public class UserDetailDto {

    private Integer id;
    private String account;
    private String name;
    private Integer branchId;
    private Integer departmentId;
    private Integer isStopped;

    private String branchName;
    private String departmentName;
}