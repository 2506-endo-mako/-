package com.example.spring_boot.repository;

import com.example.spring_boot.repository.entity.Comment;
import com.example.spring_boot.repository.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    public List<Comment> findAllByOrderByIdAsc();
}

