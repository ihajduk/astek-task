package pl.parser.nbp.service;

import pl.parser.nbp.model.RatesStatistics;

public interface CurrencyRatesService {
    RatesStatistics computeRatesStatistics(String currencyCode, String startDateString, String endDateString);
}
