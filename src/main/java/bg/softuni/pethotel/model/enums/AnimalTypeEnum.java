package bg.softuni.pethotel.model.enums;

public enum AnimalTypeEnum {
    DOG("Куче"),
    CAT("Коте");

    public final String displayLabel;

    AnimalTypeEnum(String displayLabel) {
        this.displayLabel = displayLabel;
    }
}
