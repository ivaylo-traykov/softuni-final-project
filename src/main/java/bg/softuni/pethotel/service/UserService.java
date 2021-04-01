package bg.softuni.pethotel.service;

import bg.softuni.pethotel.model.entity.UserEntity;
import bg.softuni.pethotel.model.service.UserEditServiceModel;
import bg.softuni.pethotel.model.service.UserRegisterServiceModel;
import bg.softuni.pethotel.model.view.UserProfileViewModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Collection;

public interface UserService {
    void seedInitialUsers();

    void registerUser(UserRegisterServiceModel user);

    boolean emailExists(String email);

    UserProfileViewModel getUserProfile(String name);

    UserEntity findByEmail(String email);

     UserEditServiceModel getUserInformation(Long id);

    void updateUser(UserEditServiceModel userEditServiceModel, Long id) throws IOException;

    boolean isOwnerOrModerator(Long id, UserDetails principal);

    boolean isModerator(Collection<? extends GrantedAuthority> authorities);
}
