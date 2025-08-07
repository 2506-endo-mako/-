package com.example.spring_boot.repository;

import com.example.spring_boot.repository.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    public List<Message> findByCreatedDateBetweenAndCategoryOrderByCreatedDateAsc(Date start, Date end, String category);
}


