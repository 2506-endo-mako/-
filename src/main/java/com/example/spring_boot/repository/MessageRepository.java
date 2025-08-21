package com.example.spring_boot.repository;

import com.example.spring_boot.repository.entity.Message;
import com.example.spring_boot.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    public List<Message> findByCreatedDateBetweenOrderByCreatedDateDesc(Date start,Date end);
    public List<Message> findByCreatedDateBetweenAndCategoryLikeOrderByCreatedDateAsc(Date start, Date end, String category);
}

