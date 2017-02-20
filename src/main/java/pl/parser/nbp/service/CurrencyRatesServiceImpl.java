package pl.parser.nbp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.parser.nbp.model.ExchangeTypeHolder;
import pl.parser.nbp.model.response.CurrencyStatsResponse;
import pl.parser.nbp.model.request.CurrencyStatsRequest;
import pl.parser.nbp.utils.ArithmeticUtils;
import pl.parser.nbp.utils.ParseUtils;
import pl.parser.nbp.utils.UrlUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class CurrencyRatesServiceImpl implements CurrencyRatesService {

    private final UrlUtils urlUtils;
    private final ParseUtils parseUtils;
    private final ArithmeticUtils arithmeticUtils;

    @Autowired
    public CurrencyRatesServiceImpl(UrlUtils urlUtils, ParseUtils parseUtils, ArithmeticUtils arithmeticUtils) {
        this.urlUtils = urlUtils;
        this.parseUtils = parseUtils;
        this.arithmeticUtils = arithmeticUtils;
    }

    @Override
    public CurrencyStatsResponse computeRatesStatistics(CurrencyStatsRequest currencyStatsRequest) {

        LocalDate startDate = parseUtils.parseStringToDate(currencyStatsRequest.getStartDateString());
        LocalDate endDate = parseUtils.parseStringToDate(currencyStatsRequest.getEndDateString());

        BigDecimal startDateAvgBuyRate = urlUtils.acquireAvgRateForCurrency(currencyStatsRequest.getCurrencyCode(), ExchangeTypeHolder.BUY, startDate);
        BigDecimal endDateAvgBuyRate = urlUtils.acquireAvgRateForCurrency(currencyStatsRequest.getCurrencyCode(), ExchangeTypeHolder.BUY, endDate);

        BigDecimal startDateAvgSellRate = urlUtils.acquireAvgRateForCurrency(currencyStatsRequest.getCurrencyCode(), ExchangeTypeHolder.SELL, startDate);
        BigDecimal endDateAvgSellRate = urlUtils.acquireAvgRateForCurrency(currencyStatsRequest.getCurrencyCode(), ExchangeTypeHolder.SELL, endDate);

        BigDecimal avgBuyingRate = arithmeticUtils.computeAvgRate(startDateAvgBuyRate, endDateAvgBuyRate);
        BigDecimal avgSellingRate = arithmeticUtils.computeAvgRate(startDateAvgSellRate, endDateAvgSellRate);

        BigDecimal standardDeviationBetweenRates = arithmeticUtils.computeStdDevRate(avgSellingRate, startDateAvgSellRate, endDateAvgSellRate);

        String outputAvgRate = parseUtils.formatValueToCurrencyString(avgBuyingRate);
        String outputStdDev = parseUtils.formatValueToCurrencyString(standardDeviationBetweenRates);

        return new CurrencyStatsResponse(outputAvgRate, outputStdDev);
    }
}
