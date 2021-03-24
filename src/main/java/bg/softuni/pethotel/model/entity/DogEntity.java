package bg.softuni.pethotel.model.entity;

import bg.softuni.pethotel.model.enums.DogSizeEnum;

import javax.persistence.*;

@Entity
@DiscriminatorValue("DOG")
public class DogEntity extends AnimalEntity {
    private DogSizeEnum size;

    public DogEntity() {
    }

    @Enumerated(EnumType.STRING)
    public DogSizeEnum getSize() {
        return size;
    }

    public DogEntity setSize(DogSizeEnum size) {
        this.size = size;
        return this;
    }
}
