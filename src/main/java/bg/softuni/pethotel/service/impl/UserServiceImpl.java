package bg.softuni.pethotel.service.impl;

import bg.softuni.pethotel.model.entity.RoleEntity;
import bg.softuni.pethotel.model.entity.UserEntity;
import bg.softuni.pethotel.model.enums.RoleNameEnum;
import bg.softuni.pethotel.model.service.UserEditServiceModel;
import bg.softuni.pethotel.model.service.UserRegisterServiceModel;
import bg.softuni.pethotel.model.view.AnimalViewModel;
import bg.softuni.pethotel.model.view.ReservationViewModel;
import bg.softuni.pethotel.model.view.UserListViewModel;
import bg.softuni.pethotel.model.view.UserProfileViewModel;
import bg.softuni.pethotel.repository.UserRepository;
import bg.softuni.pethotel.service.CloudinaryService;
import bg.softuni.pethotel.service.ReservationService;
import bg.softuni.pethotel.service.RoleService;
import bg.softuni.pethotel.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final ApplicationUserService applicationUserService;
    private final CloudinaryService cloudinaryService;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder,
                           RoleService roleService,
                           ApplicationUserService applicationUserService,
                           CloudinaryService cloudinaryService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.applicationUserService = applicationUserService;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public void seedInitialUsers() {

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

            UserEntity moderator = new UserEntity()
                    .setEmail("moderator@mail.com")
                    .setFirstName("Ivan")
                    .setLastName("Ivanov")
                    .setImageUrl("/images/admin_profile.png")
                    .setPassword(passwordEncoder.encode("123456"))
                    .addRole(findRole(RoleNameEnum.USER))
                    .addRole(findRole(RoleNameEnum.MODERATOR));

            UserEntity user = new UserEntity()
                    .setEmail("ggeorgiev@mail.com")
                    .setFirstName("Georgi")
                    .setLastName("Georgiev")
                    .setImageUrl("/images/user_placeholder.jpg")
                    .setPassword(passwordEncoder.encode("123456"))
                    .addRole(findRole(RoleNameEnum.USER));

            UserEntity user2 = new UserEntity()
                    .setEmail("petarp@mail.com")
                    .setFirstName("Петър")
                    .setLastName("Петров")
                    .setImageUrl("/images/user_placeholder.jpg")
                    .setPassword(passwordEncoder.encode("123456"))
                    .addRole(findRole(RoleNameEnum.USER));

            UserEntity user3 = new UserEntity()
                    .setEmail("mtz@mail.com")
                    .setFirstName("Мария")
                    .setLastName("Цветкова")
                    .setImageUrl("/images/user_placeholder.jpg")
                    .setPassword(passwordEncoder.encode("123456"))
                    .addRole(findRole(RoleNameEnum.USER));

            userRepository.saveAll(List.of(admin, moderator, user, user2, user3));
        }
    }

    private RoleEntity findRole(RoleNameEnum role) {
        return roleService.findRoleByName(role);
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
                    AnimalViewModel animalViewModel = modelMapper.map(a, AnimalViewModel.class);
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

    @Override
    public UserEditServiceModel getUserInformation(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No user with this id"));

        UserEditServiceModel editUser = modelMapper.map(user, UserEditServiceModel.class);

        editUser.setHighestRole(findHighestRole(user.getRoles()));

        return editUser;
    }

    private RoleNameEnum findHighestRole(List<RoleEntity> roles) {
        List<RoleNameEnum> roleNames = roles
                .stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toList());

        if (roleNames.contains(RoleNameEnum.ADMIN)) {
            return RoleNameEnum.ADMIN;
        } else if (roleNames.contains(RoleNameEnum.MODERATOR)) {
            return RoleNameEnum.MODERATOR;
        } else {
            return RoleNameEnum.USER;
        }
    }

    @Override
    public void updateUser(UserEditServiceModel userEditServiceModel, Long id) throws IOException {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No user with this id"));

        user.setFirstName(userEditServiceModel.getFirstName());
        user.setLastName(userEditServiceModel.getLastName());

        if (!userEditServiceModel.getImage().isEmpty()) {
            MultipartFile img = userEditServiceModel.getImage();
            String imageUrl = cloudinaryService.uploadImage(img);

            user.setImageUrl(imageUrl);
        }

        if (userEditServiceModel.getHighestRole() == null) {
            userEditServiceModel.setHighestRole(findHighestRole(user.getRoles()));
        }

        if (!findHighestRole(user.getRoles()).equals(userEditServiceModel.getHighestRole())) {
            List<RoleEntity> newRoles = getListOfRoles(userEditServiceModel.getHighestRole());
            user.setRoles(newRoles);
        }

        userRepository.save(user);
    }

    private List<RoleEntity> getListOfRoles(RoleNameEnum highestRole) {
        RoleEntity user = findRole(RoleNameEnum.USER);
        RoleEntity moderator = findRole(RoleNameEnum.MODERATOR);
        RoleEntity admin = findRole(RoleNameEnum.ADMIN);

        if (highestRole.equals(RoleNameEnum.ADMIN)) {
            return List.of(user, moderator, admin);
        } else if (highestRole.equals(RoleNameEnum.MODERATOR)) {
            return List.of(user, moderator);
        } else {
            return List.of(user);
        }
    }

    @Override
    public boolean isOwnerOrModerator(Long id, UserDetails principal) {
        UserEntity user = userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("No such user"));

        return id.equals(user.getId()) || isModerator(principal.getAuthorities());
    }

    @Override
    public boolean isModerator(Collection<? extends GrantedAuthority> authorities) {
        GrantedAuthority moderator = new SimpleGrantedAuthority("ROLE_MODERATOR");
        return authorities.contains(moderator);
    }

    @Override
    public List<UserListViewModel> getFilteredUsersList(String keyword) {
        List<UserEntity> users;

        if (keyword != null) {
            users = userRepository.searchByKeyword(keyword);
        } else {
            users = userRepository.findAll();
        }

        if (users.size() > 0) {
            return users.stream()
                    .map(u -> modelMapper.map(u, UserListViewModel.class))
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    @Override
    public Set<AnimalViewModel> getListOfAnimals(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Няма такъв потребител"));

        return user.getAnimals().stream()
                .map(a -> {
                    AnimalViewModel animalViewModel = modelMapper.map(a, AnimalViewModel.class);
                    animalViewModel.setAnimalType(a.getAnimalType().displayLabel);
                    return animalViewModel;
                })
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public List<ReservationViewModel> findAllReservations(String email) {
        UserEntity user = findByEmail(email);

        return user.getReservations().stream()
                .map(r -> {
                    ReservationViewModel newReservation = modelMapper.map(r, ReservationViewModel.class);
                    newReservation.setAnimalName(r.getAnimal().getName());
                    newReservation.setAnimalType(r.getAnimal().getAnimalType());
                    return newReservation;
                })
                .collect(Collectors.toList());
    }
}
