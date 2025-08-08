package com.example.spring_boot.repository;

import com.example.spring_boot.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public List<User> findByAccountAndPassword(String account, String password);
}
