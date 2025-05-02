package by.era.blog_project.controller;


import by.era.blog_project.dto.post.PostCreateDto;
import by.era.blog_project.dto.post.PostDto;
import by.era.blog_project.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {


    private final PostService postService;

    /**
     * Создание поста с опциональным файлом
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDto> createPost(@Valid @ModelAttribute PostCreateDto dto, @AuthenticationPrincipal UserDetails user) {
        PostDto created = postService.create(dto, user.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Получение списка постов постранично
     */
    @GetMapping
    public ResponseEntity<Page<PostDto>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
    return ResponseEntity.ok(postService.findAll(pageable));
    }

    /**
     * Удалить пост по id
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails user){
        postService.delete(id, user.getUsername());
        return ResponseEntity.noContent().build();
    }
}
