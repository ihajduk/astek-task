package pl.parser.nbp.model.request;

import lombok.Data;
import pl.parser.nbp.model.CurrencyCodes;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class CurrencyStatsRequest {

    @Pattern(regexp = CurrencyCodes.ALLOWED_CURRENCIES_PATTERN, message = "Wrong currency")
    private final String currencyCode;
    @NotNull
    private final String startDateString;
    @NotNull
    private final String endDateString;
}
