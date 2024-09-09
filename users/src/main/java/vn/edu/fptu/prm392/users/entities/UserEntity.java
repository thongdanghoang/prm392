package vn.edu.fptu.prm392.users.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;

@NamedEntityGraph(
        name = UserEntity.USER_AUTHORITIES_ENTITY_GRAPH,
        attributeNodes = {
                @NamedAttributeNode("authorities")
        }
)
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users", schema = "users")
public class UserEntity extends AbstractAuditableEntity {

    public static final String USER_AUTHORITIES_ENTITY_GRAPH = "user-authorities-entity-graph";

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
            name = "users_authorities",
            schema = "users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<AuthorityEntity> authorities = new HashSet<>();

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public static UserEntity register(String username, String password) {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }
}