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
//    @Query("SELECT DISTINCT e FROM User e INNER JOIN e.message WHERE e.message.messageId = :messageId ORDER BY e.userId")
//    List<User> find(@Param("messageId") Integer messageId);
    public List<Message> findByCreatedDateBetweenOrderByCreatedDateAsc(Date start,Date end);
    public List<Message> findByCreatedDateBetweenAndCategoryLikeOrderByCreatedDateAsc(Date start, Date end, String category);
}


