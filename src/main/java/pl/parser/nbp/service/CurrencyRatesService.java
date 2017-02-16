package pl.parser.nbp.service;

import pl.parser.nbp.model.response.CurrencyStatsResponse;
import pl.parser.nbp.model.request.CurrencyStatsRequest;

public interface CurrencyRatesService {
    CurrencyStatsResponse computeRatesStatistics(CurrencyStatsRequest currencyStatsRequest);
}
