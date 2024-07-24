package com.vti.blogapp.spectification;

import com.vti.blogapp.entity.Comment;
import com.vti.blogapp.entity.Post;
import com.vti.blogapp.form.CommentFilterForm;
import com.vti.blogapp.form.PostFilterForm;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;

public class CommentSpecification {
    public static Specification<Comment> buildSpec(CommentFilterForm form) {
        return form == null ? null : (root, query, builder) -> {
            var predicates = new LinkedList<Predicate>();

            var search = form.getSearch();
            if (StringUtils.hasText(search)) {
                var pattern = "%" + search.trim() + "%";
                var predicate = builder.like(root.get("title"), pattern);
                predicates.add(predicate);
            }

            var postId = form.getPostId();


            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
