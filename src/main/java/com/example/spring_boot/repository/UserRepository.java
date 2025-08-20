package com.example.spring_boot.repository;

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

    //ステータスの変更処理
    @Modifying
    @Query(value = "UPDATE users SET is_stopped = :is_stopped, updated_date = CURRENT_TIMESTAMP WHERE id = :id", nativeQuery = true)
    void updateStatusById(@Param("id") Integer id, @Param("is_stopped") Integer isStopped);
    List<User> findByAccountAndPassword(String account, String password);

}
