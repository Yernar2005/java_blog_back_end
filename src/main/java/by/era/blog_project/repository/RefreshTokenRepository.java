package by.era.blog_project.repository;

import by.era.blog_project.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByIdAndUser_Id(UUID id, Long userId);


}
