package bg.softuni.pethotel.init;

import bg.softuni.pethotel.model.entity.AnimalEntity;
import bg.softuni.pethotel.model.entity.CatEntity;
import bg.softuni.pethotel.model.entity.DogEntity;
import bg.softuni.pethotel.model.entity.UserEntity;
import bg.softuni.pethotel.model.enums.DogSizeEnum;
import bg.softuni.pethotel.model.enums.GenderEnum;
import bg.softuni.pethotel.service.AnimalService;
import bg.softuni.pethotel.service.RoleService;
import bg.softuni.pethotel.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DBInit implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;

    public DBInit(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        roleService.seedRoles();
        userService.seedAdminUser();
    }
}
