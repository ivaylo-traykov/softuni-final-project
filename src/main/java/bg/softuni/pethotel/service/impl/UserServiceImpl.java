package bg.softuni.pethotel.service.impl;

import bg.softuni.pethotel.model.entity.RoleEntity;
import bg.softuni.pethotel.model.entity.UserEntity;
import bg.softuni.pethotel.model.enums.RoleNameEnum;
import bg.softuni.pethotel.model.service.UserRegisterServiceModel;
import bg.softuni.pethotel.model.view.AnimalViewModel;
import bg.softuni.pethotel.model.view.UserProfileViewModel;
import bg.softuni.pethotel.repository.UserRepository;
import bg.softuni.pethotel.service.RoleService;
import bg.softuni.pethotel.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final ApplicationUserService applicationUserService;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder,
                           RoleService roleService,
                           ApplicationUserService applicationUserService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.applicationUserService = applicationUserService;
    }

    @Override
    public void seedAdminUser() {

        if (userRepository.count() == 0) {
            UserEntity admin = new UserEntity()
                    .setEmail("admin@mail.com")
                    .setFirstName("Admin")
                    .setLastName("Ivanov")
                    .setImageUrl("/images/admin_profile.png")
                    .setPassword(passwordEncoder.encode("123456"))
                    .addRole(findRole(RoleNameEnum.USER))
                    .addRole(findRole(RoleNameEnum.MODERATOR))
                    .addRole(findRole(RoleNameEnum.ADMIN));

            userRepository.save(admin);
        }
    }

    private RoleEntity findRole(RoleNameEnum role) {
        return roleService.findRoleByName(role).orElseThrow(() -> new IllegalArgumentException(role.toString() + " role not found"));
    }

    @Override
    public void registerUser(UserRegisterServiceModel user) {
        UserEntity newUser = modelMapper.map(user, UserEntity.class);

        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setImageUrl("/images/user_placeholder.jpg");

        RoleEntity userRole = findRole(RoleNameEnum.USER);

        newUser.addRole(userRole);

        userRepository.save(newUser);

        UserDetails principal = applicationUserService.loadUserByUsername(newUser.getEmail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                newUser.getPassword(),
                principal.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public UserProfileViewModel getUserProfile(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        UserProfileViewModel user = modelMapper.map(userEntity, UserProfileViewModel.class);

        user.setAnimals(userEntity.getAnimals()
                .stream()
                .map(a -> {
                    AnimalViewModel animalViewModel =  modelMapper.map(a, AnimalViewModel.class);
                    animalViewModel.setAnimalType(a.getAnimalType().displayLabel);
                    return animalViewModel;
                })
                .collect(Collectors.toSet()));

        return user;
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + "not found"));
    }
}
