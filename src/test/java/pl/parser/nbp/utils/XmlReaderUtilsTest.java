package pl.parser.nbp.utils;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import pl.parser.nbp.model.ExchangeTypeHolder;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;

@RunWith(MockitoJUnitRunner.class)
public class XmlReaderUtilsTest {

    @Mock
    private ParseUtils parseUtils;

    @InjectMocks
    private XmlReaderUtils xmlReaderUtils;

    @Test
    public void shouldAcquireAvgRateFromXmlBody() throws Exception {
        //given
        String currencyCode = "EUR";
        ExchangeTypeHolder xTypeHolder = ExchangeTypeHolder.BUY;
        String xmlBodyFilePath = "src/test/resources/table-of-currencies-body-sample";
        String xmlBody = new String(Files.readAllBytes(Paths.get(xmlBodyFilePath)));
        BigDecimal outputAvgRate = BigDecimal.valueOf(4.1397);
        Mockito.when(parseUtils.parseStringToCurrency("4,1397")).thenReturn(outputAvgRate);
        //when
        BigDecimal avgRate = xmlReaderUtils.acquireAvgRateFromXmlBody(currencyCode, xTypeHolder, xmlBody);
        //then
        Assertions.assertThat(avgRate).isEqualTo(outputAvgRate);
    }
}