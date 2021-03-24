package bg.softuni.pethotel.model.enums;

public enum DogSizeEnum {
    SMALL("Малко (до 5кг.) "),
    MEDIUM("Средно (до 20кг.)"),
    LARGE("Голямо (над 20кг.)");

    public String displayLabel;

    DogSizeEnum(String displayLabel) {
        this.displayLabel = displayLabel;
    }
}
