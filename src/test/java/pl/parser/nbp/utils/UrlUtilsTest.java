package pl.parser.nbp.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import pl.parser.nbp.model.ExchangeTypeHolder;
import pl.parser.nbp.web.RestConnector;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UrlUtilsTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @InjectMocks
    private UrlUtils urlUtils;

    @Mock
    private XmlReaderUtils xmlReaderUtils;
    @Mock
    private ParseUtils parseUtils;
    @Mock
    private RestConnector restConnector;

    /**
     * 1. Cannot mock private methods of urlUtils
     * 2. If mocking line by line, resolveUrlToRatesForDate method requires to mock the behaviour of matcher, which is a final class
     */
    @Test
    public void shouldAcquireAvgRateForCurrency() {
        //given
        String currencyCode = "EUR";
        ExchangeTypeHolder xTypeHolder = ExchangeTypeHolder.BUY;
        LocalDate localDate = LocalDate.of(2015, 2, 13);
        String urlToTableNames = "http://www.nbp.pl/kursy/xml/dir2015.txt";
        String urlToDatesForRate = "http://www.nbp.pl/kursy/xml/c030z150213.xml";
        String mockedXmlBody = "<xml>whatever</xml>";
        Mockito.when(restConnector.downloadData(urlToTableNames)).thenReturn("c030z150213");
        Mockito.when(parseUtils.parseDateToUrlFormat(localDate)).thenReturn("150213");
        Mockito.when(restConnector.downloadData(urlToDatesForRate)).thenReturn(mockedXmlBody);
        Mockito.when(xmlReaderUtils.acquireAvgRateFromXmlBody(currencyCode, xTypeHolder, mockedXmlBody)).thenReturn(BigDecimal.ONE);
        //when
        BigDecimal avgRate = urlUtils.acquireAvgRateForCurrency(currencyCode, xTypeHolder, localDate);
        //then
        Assert.assertEquals(avgRate, BigDecimal.ONE);
    }
}