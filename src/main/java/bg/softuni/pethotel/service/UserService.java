package bg.softuni.pethotel.service;

import bg.softuni.pethotel.model.entity.UserEntity;
import bg.softuni.pethotel.model.service.UserRegisterServiceModel;
import bg.softuni.pethotel.model.view.UserProfileViewModel;

public interface UserService {
    void seedAdminUser();

    void registerUser(UserRegisterServiceModel user);

    boolean emailExists(String email);

    UserProfileViewModel getUserProfile(String name);

    UserEntity findByEmail(String email);
}
