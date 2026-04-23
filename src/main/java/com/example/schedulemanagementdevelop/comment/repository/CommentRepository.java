package com.example.schedulemanagementdevelop.comment.repository;

import com.example.schedulemanagementdevelop.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c JOIN c.user u WHERE u.isDeleted = false")
    List<Comment> findAllActiveCommentsBySchedule_Id(Long scheduleId);
}
