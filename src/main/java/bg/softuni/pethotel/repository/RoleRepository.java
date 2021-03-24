package bg.softuni.pethotel.repository;

import bg.softuni.pethotel.model.entity.RoleEntity;
import bg.softuni.pethotel.model.enums.RoleNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(RoleNameEnum name);
}
