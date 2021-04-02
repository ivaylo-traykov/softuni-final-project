package bg.softuni.pethotel.model.entity;

import bg.softuni.pethotel.model.enums.SuiteTypeEnum;
import bg.softuni.pethotel.model.enums.WalkTypeEnum;

import javax.persistence.*;

@Entity
@Table(name = "dog_reservations")
public class DogReservationEntity extends ReservationEntity {

    private SuiteTypeEnum suiteType;
    private WalkTypeEnum walkType;

    public DogReservationEntity() {
    }

    @Column(name = "suite_type", nullable = false)
    @Enumerated(EnumType.STRING)
    public SuiteTypeEnum getSuiteType() {
        return suiteType;
    }

    public DogReservationEntity setSuiteType(SuiteTypeEnum suiteType) {
        this.suiteType = suiteType;
        return this;
    }

    @Column(name = "walk_type", nullable = false)
    @Enumerated(EnumType.STRING)
    public WalkTypeEnum getWalkType() {
        return walkType;
    }

    public DogReservationEntity setWalkType(WalkTypeEnum walkType) {
        this.walkType = walkType;
        return this;
    }
}
