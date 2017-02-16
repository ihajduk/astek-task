package pl.parser.nbp.model.request;

import lombok.Data;

@Data
public class CurrencyStatsRequest {
    String currencyCode;
    String startDateString;
    String endDateString;
}
