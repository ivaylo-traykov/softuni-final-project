package bg.softuni.pethotel.model.binding;

import bg.softuni.pethotel.validator.FieldMatch;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@FieldMatch(
        first = "password",
        second = "confirmPassword",
        message = "Паролите не съвпадат"
)
public class UserRegisterBindingModel {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;

    public UserRegisterBindingModel() {
    }

    @NotBlank(message = "Моля, въедете имейл адрес")
    @Email(message = "Моля, въедете валиден имейл адрес")
    public String getEmail() {
        return email;
    }

    public UserRegisterBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @NotBlank(message = "Моля, въведете име")
    @Size(min = 2, message = "Името не може да бъде по-малко от 2 символа")
    public String getFirstName() {
        return firstName;
    }

    public UserRegisterBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @NotBlank(message = "Моля, въведете име")
    @Size(min = 2, message = "Името не може да бъде по-малко от 2 символа")
    public String getLastName() {
        return lastName;
    }

    public UserRegisterBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @NotBlank(message = "Моля, въведете парола")
    @Size(min = 6, message = "Паролата не може да бъде по-малко от 6 символа")
    public String getPassword() {
        return password;
    }

    public UserRegisterBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    @NotBlank(message = "Моля, повторете паролата")
    @Size(min = 6, message = "Паролата не може да бъде по-малко от 6 символа")
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
