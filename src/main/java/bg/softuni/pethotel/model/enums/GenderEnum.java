package bg.softuni.pethotel.model.enums;

public enum GenderEnum {
    MALE("Мъжко"),
    FEMALE("Женско");

    public final String displayLabel;

    GenderEnum(String displayLabel) {
        this.displayLabel = displayLabel;
    }
}
