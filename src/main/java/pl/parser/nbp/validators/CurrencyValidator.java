package pl.parser.nbp.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.parser.nbp.model.Currencies;
import pl.parser.nbp.model.request.CurrencyStatsRequest;

@Component
public class CurrencyValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return CurrencyStatsRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CurrencyStatsRequest currencyStatsRequest = (CurrencyStatsRequest) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currencyCode", "", "Currency code argument missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDateString", "", "Start date argument missing");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDateString", "", "End date argument missing");
        if (!Currencies.isValidCurrency(currencyStatsRequest.getCurrencyCode())) {
            errors.rejectValue("currencyCode", "", "Wrong currency");
        }
    }
}