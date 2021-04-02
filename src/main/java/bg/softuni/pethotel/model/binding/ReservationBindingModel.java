package bg.softuni.pethotel.model.binding;

import bg.softuni.pethotel.model.enums.SuiteTypeEnum;
import bg.softuni.pethotel.model.enums.WalkTypeEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ReservationBindingModel {
    private LocalDate startDate;
    private LocalDate endDate;
    private Long animalId;
    private boolean ownFood;
    private SuiteTypeEnum suiteType;
    private WalkTypeEnum walkType;
    private boolean ownToilet;

    public ReservationBindingModel() {
    }

    @NotNull(message = "Моля, изберете")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "Датата не може да бъде отминала")
    public LocalDate getStartDate() {
        return startDate;
    }

    public ReservationBindingModel setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    @NotNull(message = "Моля, изберете")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "Датата не може да бъде отминала")
    public LocalDate getEndDate() {
        return endDate;
    }

    public ReservationBindingModel setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public Long getAnimalId() {
        return animalId;
    }

    public ReservationBindingModel setAnimalId(Long animalId) {
        this.animalId = animalId;
        return this;
    }

    public boolean isOwnFood() {
        return ownFood;
    }

    public ReservationBindingModel setOwnFood(boolean ownFood) {
        this.ownFood = ownFood;
        return this;
    }

    public SuiteTypeEnum getSuiteType() {
        return suiteType;
    }

    public ReservationBindingModel setSuiteType(SuiteTypeEnum suiteType) {
        this.suiteType = suiteType;
        return this;
    }

    public WalkTypeEnum getWalkType() {
        return walkType;
    }

    public ReservationBindingModel setWalkType(WalkTypeEnum walkType) {
        this.walkType = walkType;
        return this;
    }

    public boolean isOwnToilet() {
        return ownToilet;
    }

    public ReservationBindingModel setOwnToilet(boolean ownToilet) {
        this.ownToilet = ownToilet;
        return this;
    }
}
