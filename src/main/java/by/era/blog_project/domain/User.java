package by.era.blog_project.domain;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;


@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Table(name ="users")
@Entity
public class User extends BaseEntity {

    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

//    @UpdateTimestamp
//    @Column(nullable = false)
//    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name ="role_id")
    private Role role;

    @Column(nullable = false)
    private Boolean isActive = true;

}
