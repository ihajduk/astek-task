package pl.parser.nbp.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.parser.nbp.SpringConfig;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class ParseUtilsTest {

    @Autowired
    ParseUtils parseUtils;

    @Test
    public void shouldParseStringToDate() throws ParseException {
        //given
        String date = "2015-02-13";
        //when
        LocalDate parsedDate = parseUtils.parseStringToDate(date);
        //then
        Assert.assertEquals(parsedDate, LocalDate.of(2015, 2, 13));
    }

    @Test(expected = DateTimeParseException.class)
    public void shouldNotParseDateOutOfCalendar(){
        String date = "2015-02-30";
        parseUtils.parseStringToDate(date);
    }

    @Test
    public void shouldParseDateToUrl(){
        //given
        LocalDate localDate = LocalDate.of(2015, 2, 13);
        //when
        String parsedUrl = parseUtils.parseDateToUrlFormat(localDate);
        //then
        Assert.assertEquals(parsedUrl, "150213");
    }

    @Test
    public void shouldFormatValueToCurrencyString() {
        //given
        BigDecimal numericCurrency = BigDecimal.valueOf(3.14);
        //when
        String commaSeparatedCurrency = parseUtils.formatValueToCurrencyString(numericCurrency);
        //then
        Assert.assertEquals(commaSeparatedCurrency, "3,1400");
    }

    @Test
    public void should() throws ParseException {
        //given
        String value = "3,1400";
        //when
        BigDecimal parsedCurrency = parseUtils.parseStringToCurrency(value);
        //then
        Assert.assertEquals(BigDecimal.valueOf(3.14).setScale(4, BigDecimal.ROUND_HALF_EVEN), parsedCurrency);
        //TODO: profile for injecting precision
        //TODO: UrlUtils mockito usage
    }
}