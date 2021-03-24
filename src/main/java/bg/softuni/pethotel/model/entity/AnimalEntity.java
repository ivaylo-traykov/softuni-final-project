package bg.softuni.pethotel.model.entity;

import bg.softuni.pethotel.model.enums.AnimalTypeEnum;
import bg.softuni.pethotel.model.enums.GenderEnum;

import javax.persistence.*;

@Entity
@Table(name = "animals")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "animal_type", discriminatorType = DiscriminatorType.STRING)
public abstract class AnimalEntity extends BaseEntity {
    private String name;
    private GenderEnum gender;
    private String breed;
    private boolean castrated;
    private UserEntity owner;
    private String imageUrl;
    private AnimalTypeEnum animalType;

    public AnimalEntity() {
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public AnimalEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Enumerated(EnumType.STRING)
    public GenderEnum getGender() {
        return gender;
    }

    public AnimalEntity setGender(GenderEnum gender) {
        this.gender = gender;
        return this;
    }

    @Column(nullable = false)
    public String getBreed() {
        return breed;
    }

    public AnimalEntity setBreed(String breed) {
        this.breed = breed;
        return this;
    }

    @Column(nullable = false)
    public boolean isCastrated() {
        return castrated;
    }

    public AnimalEntity setCastrated(boolean castrated) {
        this.castrated = castrated;
        return this;
    }

    @ManyToOne
    @JoinColumn(name = "owner_id")
    public UserEntity getOwner() {
        return owner;
    }

    public AnimalEntity setOwner(UserEntity owner) {
        this.owner = owner;
        return this;
    }

    @Column(name = "image_url")
    public String getImageUrl() {
        return imageUrl;
    }

    public AnimalEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "animal_type", insertable = false, updatable = false)
    public AnimalTypeEnum getAnimalType() {
        return animalType;
    }

    public AnimalEntity setAnimalType(AnimalTypeEnum animalType) {
        this.animalType = animalType;
        return this;
    }
}
