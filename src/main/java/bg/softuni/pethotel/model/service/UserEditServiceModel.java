package bg.softuni.pethotel.model.service;

import bg.softuni.pethotel.model.enums.RoleNameEnum;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserEditServiceModel {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String imageUrl;
    private MultipartFile image;
    private RoleNameEnum highestRole;

    public UserEditServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public UserEditServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEditServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @NotBlank(message = "Моля, въведете име")
    @Size(min = 2, message = "Името не може да бъде по-малко от 2 символа")
    public String getFirstName() {
        return firstName;
    }

    public UserEditServiceModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @NotBlank(message = "Моля, въведете име")
    @Size(min = 2, message = "Името не може да бъде по-малко от 2 символа")
    public String getLastName() {
        return lastName;
    }

    public UserEditServiceModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public RoleNameEnum getHighestRole() {
        return highestRole;
    }

    public UserEditServiceModel setHighestRole(RoleNameEnum highestRole) {
        this.highestRole = highestRole;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserEditServiceModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public MultipartFile getImage() {
        return image;
    }

    public UserEditServiceModel setImage(MultipartFile image) {
        this.image = image;
        return this;
    }
}
