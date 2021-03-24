package bg.softuni.pethotel.service;

import bg.softuni.pethotel.model.entity.RoleEntity;
import bg.softuni.pethotel.model.enums.RoleNameEnum;
import java.util.Optional;

public interface RoleService {
    void seedRoles();

    Optional<RoleEntity> findRoleByName(RoleNameEnum name);
}
