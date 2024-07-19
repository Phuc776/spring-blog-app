package com.vti.blogapp.repository;

import com.vti.blogapp.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository
        extends JpaRepository<Comment, Comment.PrimaryKey> {

    // VD: Lấy ra tất cả comment theo post id
    Page<Comment> findByPostId(Long postId, Pageable pageable);


}
