package bg.softuni.pethotel.model.view;

import bg.softuni.pethotel.model.enums.AnimalTypeEnum;
import bg.softuni.pethotel.model.enums.SuiteTypeEnum;
import bg.softuni.pethotel.model.enums.WalkTypeEnum;

import java.time.LocalDate;

public class ReservationViewModel {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String animalName;
    private AnimalTypeEnum animalType;
    private boolean ownFood;
    private SuiteTypeEnum suiteType;
    private WalkTypeEnum walkType;
    private boolean ownToilet;

    public ReservationViewModel() {
    }

    public Long getId() {
        return id;
    }

    public ReservationViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public ReservationViewModel setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public ReservationViewModel setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getAnimalName() {
        return animalName;
    }

    public AnimalTypeEnum getAnimalType() {
        return animalType;
    }

    public ReservationViewModel setAnimalType(AnimalTypeEnum animalType) {
        this.animalType = animalType;
        return this;
    }

    public ReservationViewModel setAnimalName(String animalName) {
        this.animalName = animalName;
        return this;
    }

    public boolean isOwnFood() {
        return ownFood;
    }

    public ReservationViewModel setOwnFood(boolean ownFood) {
        this.ownFood = ownFood;
        return this;
    }

    public SuiteTypeEnum getSuiteType() {
        return suiteType;
    }

    public ReservationViewModel setSuiteType(SuiteTypeEnum suiteType) {
        this.suiteType = suiteType;
        return this;
    }

    public WalkTypeEnum getWalkType() {
        return walkType;
    }

    public ReservationViewModel setWalkType(WalkTypeEnum walkType) {
        this.walkType = walkType;
        return this;
    }

    public boolean isOwnToilet() {
        return ownToilet;
    }

    public ReservationViewModel setOwnToilet(boolean ownToilet) {
        this.ownToilet = ownToilet;
        return this;
    }
}
