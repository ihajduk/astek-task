package pl.parser.nbp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.parser.nbp.exceptions.UnregisteredDateException;
import pl.parser.nbp.model.ExchangeType;
import pl.parser.nbp.web.RestConnector;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UrlUtils {

    private final XmlReaderUtils xmlReaderUtils;
    private final ParseUtils parseUtils;
    private final RestConnector restConnector;
    private static final String NBP_URL = "http://www.nbp.pl/kursy/xml/";
    private static final String NBP_URL_DIR = NBP_URL+"dir";

    @Autowired
    public UrlUtils(XmlReaderUtils xmlReaderUtils, ParseUtils parseUtils, RestConnector restConnector) {
        this.xmlReaderUtils = xmlReaderUtils;
        this.parseUtils = parseUtils;
        this.restConnector = restConnector;
    }

    public BigDecimal acquireAvgRateForCurrency(String currency, ExchangeType xType, LocalDate date) {
        String urlToRatesForDate = resolveUrlToRatesForDate(date);
        String responseXmlData = restConnector.downloadData(urlToRatesForDate);
        return xmlReaderUtils.acquireAvgRateFromXmlFile(currency, xType, responseXmlData);
    }

    private String resolveUrlToRatesForDate(LocalDate date) {

        String urlToTableNames = String.format("%s%s.txt", NBP_URL_DIR, getYearSuffix(date));
        String responseTableNamesData = restConnector.downloadData(urlToTableNames);

        String urlParsedDate = parseUtils.parseDateToUrlFormat(date);
        Matcher matcher = Pattern.compile("c[0-9]{0,3}z"+urlParsedDate).matcher(responseTableNamesData);
        if(matcher.find()){
            return String.format("%s%s.xml", NBP_URL, matcher.group(0));
        }
        throw new RuntimeException(new UnregisteredDateException("Given date: " + date + " is not recorded in currency data"));
    }

    private String getYearSuffix(LocalDate date) {
        return date.getYear() == LocalDate.now().getYear() ? "" : Integer.valueOf(date.getYear()).toString();
    }
}
