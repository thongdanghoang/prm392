package vn.edu.fptu.prm392.users.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.fptu.prm392.users.entities.AuthorityEntity;
import vn.edu.fptu.prm392.users.entities.UserEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @EntityGraph(UserEntity.USER_AUTHORITIES_ENTITY_GRAPH)
    @Query("SELECT u FROM UserEntity u WHERE u.username = :username")
    Optional<UserEntity> findByUsernameWithAuthorities(String username);

    @Query("SELECT a FROM AuthorityEntity a WHERE a.role = :role")
    Optional<AuthorityEntity> findUserByRole(String role);
}

