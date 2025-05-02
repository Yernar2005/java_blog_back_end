package by.era.blog_project.dto.post;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostCreateDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private MultipartFile image;

}
