package com.example.spring_boot.repository;

//import com.example.spring_boot.dto.UserWithDetailsDto;
import com.example.spring_boot.dto.UserDetailDto;
import com.example.spring_boot.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public List<User> findAll();
    public Optional<User> findByAccount(String account);

    //ユーザー管理画面表示
    @Query("SELECT new com.example.spring_boot.dto.UserDetailDto(" +
            "u.id, u.account, u.name, u.branchId, u.departmentId, u.isStopped, b.name, d.name) " +
            "FROM User u, Branch b, Department d " +
            "WHERE u.branchId = b.id AND u.departmentId = d.id")
    List<UserDetailDto> findAllUserDetails();

    //ステータスの変更処理
    @Modifying
    @Query(value = "UPDATE users SET is_stopped = :is_stopped, updated_date = CURRENT_TIMESTAMP WHERE id = :id", nativeQuery = true)
    void updateStatusById(@Param("id") Integer id, @Param("is_stopped") Integer isStopped);

}
