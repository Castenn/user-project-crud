package ua.casten.user.project.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "users")
public class User {

    private enum Role {
        USER, ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Nickname cannot be null")
    @Size(min = 4, max = 25, message = "Nickname must be between 4 and 25 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,24}[a-zA-Z0-9]$",
            message = "Nickname should be valid")
    @Column(name = "nickname", length = 50, nullable = false, unique = true)
    private String nickname;

    @Email(message = "Email should be valid")
    @NotNull(message = "Email cannot be null")
    @Size(min = 5, max = 50, message = "Email must be between 5 and 50 characters")
    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @NotNull(message = "Password cannot be null")
    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false, updatable = false)
    private Role role;
}
