package vn.edu.fptu.prm392.users.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "authorities", schema = "users")
public class AuthorityEntity extends AbstractBaseEntity {

    @Column(name = "role", length = 64, nullable = false, unique = true)
    private String role;
}
