package bg.softuni.pethotel.service.impl;

import bg.softuni.pethotel.model.entity.RoleEntity;
import bg.softuni.pethotel.model.entity.UserEntity;
import bg.softuni.pethotel.model.enums.RoleNameEnum;
import bg.softuni.pethotel.repository.UserRepository;
import bg.softuni.pethotel.service.CloudinaryService;
import bg.softuni.pethotel.service.RoleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    PasswordEncoder passwordEncoder;
    RoleService roleService;
    ApplicationUserService applicationUserService;
    CloudinaryService cloudinaryService;

    UserServiceImpl service;
    @BeforeEach
    public void setUp() {
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        roleService = Mockito.mock(RoleServiceImpl.class);
        applicationUserService = Mockito.mock(ApplicationUserService.class);
        cloudinaryService = Mockito.mock(CloudinaryServiceImpl.class);
        ModelMapper modelMapper = new ModelMapper();

        service = new UserServiceImpl(
                userRepository,
                modelMapper,
                passwordEncoder,
                roleService,
                applicationUserService,
                cloudinaryService);

        UserEntity user = new UserEntity()
                .setEmail("Test@mail.com")
                .setFirstName("Ivan")
                .setLastName("Ivanov")
                .setPassword("123456")
                .addRole(new RoleEntity().setName(RoleNameEnum.USER))
                .addRole(new RoleEntity().setName(RoleNameEnum.MODERATOR));

        userRepository.save(user);
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void emailExists_whenUserExists() {
        Assertions.assertTrue(service.emailExists("Test@mail.com"));
    }

    @Test
    void emailExists_whenUserDoNotExists() {
        Assertions.assertFalse(service.emailExists("nonExisting@mail.com"));
    }


}