package com.vti.blogapp.service;

import com.vti.blogapp.dto.PostDto;
import com.vti.blogapp.entity.Post;
import com.vti.blogapp.form.PostCreateForm;
import com.vti.blogapp.form.PostFilterForm;
import com.vti.blogapp.form.PostUpdateForm;
import com.vti.blogapp.repository.PostRepository;
import com.vti.blogapp.spectification.PostSpecification;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService{
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    @Override
    public Page<PostDto> findAll(PostFilterForm form, Pageable pageable) {
        var spec = PostSpecification.buildSpec(form);
        return postRepository.findAll(spec, pageable) // Page<Post>
                .map(post -> modelMapper
                        .map(post, PostDto.class)
                        .withSelfRel()); // Page<PostDto>
    }

    @Override
    public PostDto findById(Long id) {
        return postRepository.findById(id)
                .map(post -> modelMapper
                        .map(post, PostDto.class)
                        .withSelfRel())
                .orElse(null);
        // new Function
        // lambda
        // method ref (In Usage)

        // optional (Java 8)
    }

    @Override
    public PostDto create(PostCreateForm form) {
        var post = modelMapper.map(form, Post.class);
        var savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostDto update(Long id, PostUpdateForm form) {
        var optional = postRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }

        var post = optional.get();
        modelMapper.map(form, post);
        var savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }
}
