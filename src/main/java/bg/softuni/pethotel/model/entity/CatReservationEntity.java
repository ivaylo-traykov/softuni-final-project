package bg.softuni.pethotel.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cat_reservations")
public class CatReservationEntity extends ReservationEntity {
    private boolean ownToilet;

    public CatReservationEntity() {
    }

    @Column(name = "own_toilet", nullable = false)
    public boolean isOwnToilet() {
        return ownToilet;
    }

    public CatReservationEntity setOwnToilet(boolean ownToilet) {
        this.ownToilet = ownToilet;
        return this;
    }
}
