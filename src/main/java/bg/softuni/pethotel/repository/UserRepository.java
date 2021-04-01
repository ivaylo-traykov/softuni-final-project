package bg.softuni.pethotel.repository;

import bg.softuni.pethotel.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT e FROM UserEntity e WHERE CONCAT(e.email, e.firstName, e.lastName) LIKE %?1%")
    List<UserEntity> searchByKeyword(String keyword);
}
