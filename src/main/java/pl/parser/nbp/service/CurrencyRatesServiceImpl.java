package pl.parser.nbp.service;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.parser.nbp.model.Currencies;
import pl.parser.nbp.model.ExchangeTypeHolder;
import pl.parser.nbp.model.RatesStatistics;
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

    public RatesStatistics computeRatesStatistics(String currency, String startDateString, String endDateString) {
        Preconditions.checkArgument(Currencies.isValidCurrency(currency), "Wrong currency");

        LocalDate startDate = parseUtils.parseStringToDate(startDateString);
        LocalDate endDate = parseUtils.parseStringToDate(endDateString);

        BigDecimal startDateAvgBuyRate = urlUtils.acquireAvgRateForCurrency(currency, ExchangeTypeHolder.BUY, startDate);
        BigDecimal endDateAvgBuyRate = urlUtils.acquireAvgRateForCurrency(currency, ExchangeTypeHolder.BUY, endDate);

        BigDecimal startDateAvgSellRate = urlUtils.acquireAvgRateForCurrency(currency, ExchangeTypeHolder.SELL, startDate);
        BigDecimal endDateAvgSellRate = urlUtils.acquireAvgRateForCurrency(currency, ExchangeTypeHolder.SELL, endDate);

        BigDecimal avgBuyingRate = arithmeticUtils.computeAvgRate(startDateAvgBuyRate, endDateAvgBuyRate);
        BigDecimal avgSellingRate = arithmeticUtils.computeAvgRate(startDateAvgSellRate, endDateAvgSellRate);

        BigDecimal standardDeviationBetweenRates = arithmeticUtils.computeStdDevRate(avgSellingRate, startDateAvgSellRate, endDateAvgSellRate);

        String outputAvgRate = parseUtils.formatValueToCurrencyString(avgBuyingRate);
        String outputStdDev = parseUtils.formatValueToCurrencyString(standardDeviationBetweenRates);

        return new RatesStatistics(outputAvgRate, outputStdDev);
    }
}
