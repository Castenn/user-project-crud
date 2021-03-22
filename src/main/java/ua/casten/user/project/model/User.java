package ua.casten.user.project.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "[A-Z][a-z]{3,49}",
            message = "Must start with a capital letter followed by one or more lowercase letters")
    @Column(name = "nickname", length = 50, nullable = false, unique = true)
    private String nickname;

    @Pattern(regexp = "[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}", message = "Must be a valid e-mail address")
    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

//    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}",
//            message = "Must be minimum 6 characters, at least one letter and one number")
    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @ManyToOne
    @Column(name = "role_id", nullable = false)
    private Role role;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_has_project",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Project> projects;
}
