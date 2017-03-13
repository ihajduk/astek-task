package pl.parser.nbp.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import pl.parser.nbp.model.ExchangeTypeHolder;
import pl.parser.nbp.model.request.CurrencyStatsRequest;
import pl.parser.nbp.model.response.CurrencyStatsResponse;
import pl.parser.nbp.utils.ArithmeticUtils;
import pl.parser.nbp.utils.ParseUtils;
import pl.parser.nbp.utils.UrlUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyRatesServiceImplTest {

    @Mock
    private ParseUtils parseUtils;
    @Mock
    private UrlUtils urlUtils;
    @Mock
    private ArithmeticUtils arithmeticUtils;
    @InjectMocks
    private CurrencyRatesServiceImpl currencyRatesService;

    @Test
    public void shouldComputeRatesStatistics() throws Exception {
        //given
        String dateString = "2015-03-17";
        String currencyCode = "EUR";
        LocalDate localDate = LocalDate.of(2015, 3, 17);
        BigDecimal decimalBuy = BigDecimal.valueOf(4.0887);
        BigDecimal decimalSell = BigDecimal.valueOf(4.1713);
        CurrencyStatsRequest currencyStatsRequest = new CurrencyStatsRequest(currencyCode, dateString, dateString);
        Mockito.when(parseUtils.parseStringToDate(dateString)).thenReturn(localDate);
        Mockito.when(urlUtils.acquireAvgRateForCurrency(currencyCode, ExchangeTypeHolder.BUY, localDate)).thenReturn(decimalBuy);
        Mockito.when(urlUtils.acquireAvgRateForCurrency(currencyCode, ExchangeTypeHolder.SELL, localDate)).thenReturn(decimalSell);
        Mockito.when(arithmeticUtils.computeAvgRate(decimalBuy, decimalBuy)).thenReturn(decimalBuy);
        Mockito.when(arithmeticUtils.computeAvgRate(decimalSell, decimalSell)).thenReturn(decimalSell);
        Mockito.when(arithmeticUtils.computeStdDevRate(decimalSell, decimalSell, decimalSell)).thenReturn(BigDecimal.ZERO);
        Mockito.when(parseUtils.formatValueToCurrencyString(decimalBuy)).thenReturn("4,0887");
        Mockito.when(parseUtils.formatValueToCurrencyString(BigDecimal.ZERO)).thenReturn("0,0000");

        //when
        CurrencyStatsResponse currencyStatsResponse = currencyRatesService.computeRatesStatistics(currencyStatsRequest);

        //then
        Assert.assertEquals("4,0887", currencyStatsResponse.getAvgBuyingRate());
        Assert.assertEquals("0,0000", currencyStatsResponse.getStdDevSellingRate());
    }
}