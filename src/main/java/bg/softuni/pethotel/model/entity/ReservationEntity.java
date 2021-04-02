package bg.softuni.pethotel.model.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
@Inheritance(strategy = InheritanceType.JOINED)
public class ReservationEntity extends BaseEntity {
    private LocalDate startDate;
    private LocalDate endDate;
    private AnimalEntity animal;
    private UserEntity owner;
    private boolean ownFood;

    public ReservationEntity() {
    }

    @Column(name = "start_date", nullable = false)
    public LocalDate getStartDate() {
        return startDate;
    }

    public ReservationEntity setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    @Column(name = "end_date", nullable = false)
    public LocalDate getEndDate() {
        return endDate;
    }

    public ReservationEntity setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    @ManyToOne
    public AnimalEntity getAnimal() {
        return animal;
    }

    public ReservationEntity setAnimal(AnimalEntity animal) {
        this.animal = animal;
        return this;
    }

    @ManyToOne
    public UserEntity getOwner() {
        return owner;
    }

    public ReservationEntity setOwner(UserEntity owner) {
        this.owner = owner;
        return this;
    }

    @Column(name = "own_food", nullable = false)
    public boolean isOwnFood() {
        return ownFood;
    }

    public ReservationEntity setOwnFood(boolean ownFood) {
        this.ownFood = ownFood;
        return this;
    }
}
