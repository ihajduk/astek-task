package pl.parser.nbp.utils;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@RunWith(MockitoJUnitRunner.class)
public class ParseUtilsTest {

    //TODO: Inject somehow from properties??
    private static final int PRECISION = 4;

    private ParseUtils parseUtils = new ParseUtils(PRECISION);

    @Test
    public void shouldParseStringToDate() throws Exception {
        //given
        String date = "2015-02-13";
        //when
        LocalDate parsedDate = parseUtils.parseStringToDate(date);
        //then
        Assertions.assertThat(parsedDate).isEqualTo(LocalDate.of(2015, 2, 13));
    }

    @Test(expected = DateTimeParseException.class)
    public void shouldNotParseDateOutOfCalendar() throws Exception {
        String date = "2015-02-30";
        parseUtils.parseStringToDate(date);
    }

    @Test
    public void shouldParseDateToUrl() throws Exception {
        //given
        LocalDate localDate = LocalDate.of(2015, 2, 13);
        //when
        String parsedUrl = parseUtils.parseDateToUrlFormat(localDate);
        //then
        Assertions.assertThat(parsedUrl).isEqualTo("150213");
    }

    @Test
    public void shouldFormatValueToCurrencyString() throws Exception {
        //given
        BigDecimal numericCurrency = BigDecimal.valueOf(3.14).setScale(PRECISION, BigDecimal.ROUND_HALF_EVEN);
        //when
        String commaSeparatedCurrency = parseUtils.formatValueToCurrencyString(numericCurrency);
        //then
        Assertions.assertThat(commaSeparatedCurrency).isEqualTo("3,1400");
    }

    @Test
    public void shouldParseStringToCurrency() throws Exception {
        //given
        String value = "3,1400";
        //when
        BigDecimal parsedCurrency = parseUtils.parseStringToCurrency(value);
        //then
        Assertions.assertThat(parsedCurrency).isEqualTo(BigDecimal.valueOf(3.14).setScale(PRECISION, BigDecimal.ROUND_HALF_EVEN));
    }
}