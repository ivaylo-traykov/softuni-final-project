package bg.softuni.pethotel.model.binding;

import bg.softuni.pethotel.model.enums.AnimalTypeEnum;
import bg.softuni.pethotel.model.enums.DogSizeEnum;
import bg.softuni.pethotel.model.enums.GenderEnum;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AnimalRegisterBindingModel {
    private AnimalTypeEnum type;
    private String name;
    private GenderEnum gender;
    private String breed;
    private boolean castrated;
    private DogSizeEnum size;
    private MultipartFile image;

    public AnimalRegisterBindingModel() {
    }

    @NotNull(message = "Моля, изберете")
    public AnimalTypeEnum getType() {
        return type;
    }

    public AnimalRegisterBindingModel setType(AnimalTypeEnum type) {
        this.type = type;
        return this;
    }

    @NotEmpty(message = "Моля, посочете име")
    public String getName() {
        return name;
    }

    public AnimalRegisterBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    @NotNull(message = "Моля, изберете")
    public GenderEnum getGender() {
        return gender;
    }

    public AnimalRegisterBindingModel setGender(GenderEnum gender) {
        this.gender = gender;
        return this;
    }

    public String getBreed() {
        return breed;
    }

    public AnimalRegisterBindingModel setBreed(String breed) {
        this.breed = breed;
        return this;
    }

    @NotNull(message = "Моля посочете. Ако не сте сигурни изберете опция \"Не\"")
    public boolean isCastrated() {
        return castrated;
    }

    public AnimalRegisterBindingModel setCastrated(boolean castrated) {
        this.castrated = castrated;
        return this;
    }

    public DogSizeEnum getSize() {
        return size;
    }

    public AnimalRegisterBindingModel setSize(DogSizeEnum size) {
        this.size = size;
        return this;
    }

    public MultipartFile getImage() {
        return image;
    }

    public AnimalRegisterBindingModel setImage(MultipartFile image) {
        this.image = image;
        return this;
    }
}
