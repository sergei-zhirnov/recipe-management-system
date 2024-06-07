package recipes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="users", schema="auth")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "auth.users_id_seq", allocationSize = 1)
    private Long id;

    @Column(name="email")
    @Pattern(regexp = ".+@.+\\..+")
    private String email;

    @Column(name="password_hash")
    @NotBlank
    @Size(min = 8)
    private String password;

    public AppUser(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public Collection<GrantedAuthority> getAuthority() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

}
