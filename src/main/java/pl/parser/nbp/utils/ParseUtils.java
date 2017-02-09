package pl.parser.nbp.utils;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class ParseUtils {

    private static final String NBP_URL_DATE_PATTERN = "yyMMdd";
    private final NumberFormat commaSeparatedNumberFormat;
    private final int precision;

    public ParseUtils(@Value("${currency.precision}") int precision) {
            this.precision = precision;
            commaSeparatedNumberFormat = NumberFormat.getInstance(Locale.FRANCE);
            commaSeparatedNumberFormat.setMinimumFractionDigits(precision);
    }

    public LocalDate parseStringToDate(String dateString) {
        Preconditions.checkNotNull(dateString, "String cannot be null");
        return LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public String parseDateToUrlFormat(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(NBP_URL_DATE_PATTERN));
    }

    public String formatValueToCurrencyString(BigDecimal value) {
        return commaSeparatedNumberFormat.format(value);
    }

    public BigDecimal parseStringToCurrency(String value) throws ParseException {
        return BigDecimal.valueOf(commaSeparatedNumberFormat.parse(value).doubleValue()).setScale(precision, BigDecimal.ROUND_HALF_EVEN);
    }
}