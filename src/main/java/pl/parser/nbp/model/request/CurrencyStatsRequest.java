package pl.parser.nbp.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class CurrencyStatsRequest {
    @Pattern(regexp = "EUR|USD|CHF|GBP", message = "Wrong currency")
    private String currencyCode;
    @NotNull
    private String startDateString;
    @NotNull
    private String endDateString;
}
