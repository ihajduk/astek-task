package pl.parser.nbp.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by iwha on 1/14/2017.
 */
@Component
public class ParseUtils {

    public LocalDate parseStringToDate(String dateString) {
        if(dateString==null){
            throw new NullPointerException("Not enough arguments provided");
        }
        return LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public String parseDateToUrlFormat(LocalDate date) {
        LocalDateTime tempDateTime = LocalDateTime.of(date, LocalTime.MIN);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        return tempDateTime.format(formatter).substring(0,6);
    }

    public String formatValueToCurrencyString(BigDecimal value){
        NumberFormat currencySeparatorFormat = NumberFormat.getInstance(Locale.FRANCE);
        currencySeparatorFormat.setMinimumFractionDigits(4);
        return currencySeparatorFormat.format(value);
    }

    public BigDecimal parseValueToCurrency(String value) throws ParseException {
        NumberFormat currencySeparatorFormat = NumberFormat.getInstance(Locale.FRANCE);
        return BigDecimal.valueOf(currencySeparatorFormat.parse(value).doubleValue()).setScale(4);
    }

}
