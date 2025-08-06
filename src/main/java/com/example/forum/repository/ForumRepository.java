package com.example.forum.repository;

import com.example.forum.repository.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Users, Integer> {
}

