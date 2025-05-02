package by.era.blog_project.dto.post;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private String imagePath;       // URL вида /uploads/filename.jpg
    private String authorUsername;
    private Instant createdAt;
    private Instant updatedAt;

}
