package pl.parser.nbp.model.request;

import lombok.Data;
import pl.parser.nbp.model.CurrencyCodes;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class CurrencyStatsRequest implements Serializable {

    @Pattern(regexp = CurrencyCodes.ALLOWED_CURRENCIES, message = "Wrong currency")
    private String currencyCode;
    @NotNull
    private String startDateString;
    @NotNull
    private String endDateString;
}
