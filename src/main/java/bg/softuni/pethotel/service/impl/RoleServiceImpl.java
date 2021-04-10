package bg.softuni.pethotel.service.impl;

import bg.softuni.pethotel.model.entity.RoleEntity;
import bg.softuni.pethotel.model.enums.RoleNameEnum;
import bg.softuni.pethotel.repository.RoleRepository;
import bg.softuni.pethotel.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void seedRoles() {

        if (roleRepository.count() == 0) {

            RoleEntity user = new RoleEntity().setName(RoleNameEnum.USER);
            RoleEntity moderator = new RoleEntity().setName(RoleNameEnum.MODERATOR);
            RoleEntity admin = new RoleEntity().setName(RoleNameEnum.ADMIN);

            roleRepository.saveAll(List.of(user, moderator, admin));
        }
    }

    @Override
    public RoleEntity findRoleByName(RoleNameEnum name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException(name.toString() + " role not found"));
    }
}
