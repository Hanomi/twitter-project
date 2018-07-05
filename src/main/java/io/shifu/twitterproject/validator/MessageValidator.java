package io.shifu.twitterproject.validator;

import io.shifu.twitterproject.model.Message;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for {@link Message} class,
 * implements {@link Validator}
 */

@Component
public class MessageValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Message.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Message message = (Message) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text", "NotEmpty");
        if (message.getText().length() > 250) {
            errors.rejectValue("text", "Size.messageForm.text");
        }
    }
}
