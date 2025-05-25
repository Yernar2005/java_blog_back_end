package by.era.blog_project.service.impl;


import by.era.blog_project.domain.Post;
import by.era.blog_project.domain.User;
import by.era.blog_project.dto.post.PostCreateDto;
import by.era.blog_project.dto.post.PostDto;
import by.era.blog_project.repository.PostRepository;
import by.era.blog_project.repository.UserRepository;
import by.era.blog_project.service.FileStorageService;
import by.era.blog_project.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;


    private PostDto toDto(Post post) {
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getImagePath(),
                post.getAuthor().getUsername(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    @Override
    @Transactional
    public PostDto create(PostCreateDto dto, String authorEmail) {

        User author = userRepository.findByEmail(authorEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Post post = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(author)
                .createdAt(Instant.now())
                .build();

        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            String path = fileStorageService.storageFile(dto.getImage());
            post.setImagePath(path);
        }

        Post saved = postRepository.save(post);
        return toDto(saved);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(this::toDto);
    }


    @Override
    @Transactional
    public void delete(Long postId, String currentUserEmail) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found: " + postId));

        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + currentUserEmail));

        boolean isAuthor = post.getAuthor().getEmail().equals(currentUserEmail);
        boolean isAdmin  = "ROLE_ADMIN".equals(currentUser.getRole().getName());

        if (!isAuthor && !isAdmin) {
            throw new AccessDeniedException("You don't have permission to delete this post");
        }

        postRepository.delete(post);
    }

}
