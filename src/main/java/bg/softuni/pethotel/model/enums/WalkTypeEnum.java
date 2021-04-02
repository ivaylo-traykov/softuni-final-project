package bg.softuni.pethotel.model.enums;

public enum WalkTypeEnum {
    INDIVIDUAL("Индивидуална"),
    COMBINED("Обща");

    public final String displayLabel;

    WalkTypeEnum(String displayLabel) {
        this.displayLabel = displayLabel;
    }
}
