package bg.softuni.pethotel.model.enums;

public enum SuiteTypeEnum {
    INDOOR("Вътре"),
    OUTDOOR("Отвън");

    public final String displayLabel;

    SuiteTypeEnum(String displayLabel) {
        this.displayLabel = displayLabel;
    }
}
