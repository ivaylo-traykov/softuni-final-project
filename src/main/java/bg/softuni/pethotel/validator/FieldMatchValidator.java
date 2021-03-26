package bg.softuni.pethotel.validator;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstField;
    private String secondField;
    private String message;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.firstField = constraintAnnotation.first();
        this.secondField = constraintAnnotation.second();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(o);
        Object firstFieldValue = beanWrapper.getPropertyValue(firstField);
        Object secondFieldValue = beanWrapper.getPropertyValue(secondField);

        boolean valid;

        if (firstFieldValue == null) {
            valid = secondFieldValue == null;
        } else {
            valid = firstFieldValue.equals(secondFieldValue);
        }

        if (!valid) {
            constraintValidatorContext
            .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstField)
                    .addConstraintViolation()
                .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(secondField)
                    .addConstraintViolation()
                .disableDefaultConstraintViolation();
        }

        return valid;
    }
}
