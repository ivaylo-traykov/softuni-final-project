package bg.softuni.pethotel.model.view;

import bg.softuni.pethotel.model.enums.DogSizeEnum;
import bg.softuni.pethotel.model.enums.GenderEnum;

public class AnimalViewModel {
    private Long id;
    private String name;
    private GenderEnum gender;
    private String breed;
    private boolean castrated;
    private DogSizeEnum size;
    private String imageUrl;
    private String animalType;

    public Long getId() {
        return id;
    }

    public AnimalViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AnimalViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public AnimalViewModel setGender(GenderEnum gender) {
        this.gender = gender;
        return this;
    }

    public String getBreed() {
        return breed;
    }

    public AnimalViewModel setBreed(String breed) {
        this.breed = breed;
        return this;
    }

    public boolean isCastrated() {
        return castrated;
    }

    public AnimalViewModel setCastrated(boolean castrated) {
        this.castrated = castrated;
        return this;
    }

    public DogSizeEnum getSize() {
        return size;
    }

    public AnimalViewModel setSize(DogSizeEnum size) {
        this.size = size;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public AnimalViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getAnimalType() {
        return animalType;
    }

    public AnimalViewModel setAnimalType(String animalType) {
        this.animalType = animalType;
        return this;
    }
}
