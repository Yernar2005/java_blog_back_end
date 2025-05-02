package by.era.blog_project.service;


import by.era.blog_project.dto.post.PostCreateDto;
import by.era.blog_project.dto.post.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PostDto create(PostCreateDto dto, String authorUsername);
    Page<PostDto> findAll(Pageable pageable);
    void delete(Long postId, String authorUsername);

}
