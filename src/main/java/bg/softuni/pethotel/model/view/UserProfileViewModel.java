package bg.softuni.pethotel.model.view;

import java.util.Set;

public class UserProfileViewModel {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String imageUrl;
    private Set<AnimalViewModel> animalViewModels;

    public UserProfileViewModel() {
    }

    public Long getId() {
        return id;
    }

    public UserProfileViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserProfileViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserProfileViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserProfileViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserProfileViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Set<AnimalViewModel> getAnimals() {
        return animalViewModels;
    }

    public UserProfileViewModel setAnimals(Set<AnimalViewModel> animals) {
        this.animalViewModels = animals;
        return this;
    }
}
