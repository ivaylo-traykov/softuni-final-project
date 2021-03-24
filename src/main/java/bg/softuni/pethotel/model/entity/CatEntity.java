package bg.softuni.pethotel.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CAT")
public class CatEntity extends AnimalEntity {

    public CatEntity() {
    }
}
