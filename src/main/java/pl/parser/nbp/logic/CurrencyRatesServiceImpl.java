package pl.parser.nbp.logic;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;
import pl.parser.nbp.exceptions.InvalidCurrencyException;
import pl.parser.nbp.exceptions.UnregisteredDateException;
import pl.parser.nbp.utils.StringParserUtil;
import pl.parser.nbp.utils.XmlReaderUtils;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by iwha on 1/14/2017.
 */
@Service
public class CurrencyRatesServiceImpl implements CurrencyRatesService {

    private StringParserUtil stringParserUtil;
    private XmlReaderUtils xmlReaderUtils;
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public CurrencyRatesServiceImpl(StringParserUtil stringParserUtil, XmlReaderUtils xmlReaderUtils) {
        this.stringParserUtil = stringParserUtil;
        this.xmlReaderUtils = xmlReaderUtils;
    }

    public Pair<String, String> computeEntryValues(String currency, String startDateString, String endDateString) {
        Pair<String, String> outputValues = new Pair<>("","");
        try {
            if (!Currencies.isValidCurrency(currency)){
                throw new InvalidCurrencyException("Wrong currency");
            }
            LocalDate startDate = stringParserUtil.parseStringToDate(startDateString);
            LocalDate endDate = stringParserUtil.parseStringToDate(endDateString);

            BigDecimal startDateAvgBuyRate = acquireAvgRateForCurrency(currency, ExchangeType.BUY, startDate);
            BigDecimal endDateAvgBuyRate = acquireAvgRateForCurrency(currency, ExchangeType.BUY, endDate);

            BigDecimal startDateAvgSellRate = acquireAvgRateForCurrency(currency, ExchangeType.SELL, startDate);
            BigDecimal endDateAvgSellRate = acquireAvgRateForCurrency(currency, ExchangeType.SELL, endDate);

            BigDecimal avgBuyingRate = computeAvgRate(startDateAvgBuyRate, endDateAvgBuyRate);
            BigDecimal avgSellingRate = computeAvgRate(startDateAvgSellRate, endDateAvgSellRate);

            BigDecimal standardDeviationBetweenRates = computeStdDevBetweenRates(avgSellingRate, startDateAvgSellRate, endDateAvgSellRate);

            String outputAvgRate = stringParserUtil.formatValueToCurrencyString(avgBuyingRate);
            String outputStdDev = stringParserUtil.formatValueToCurrencyString(standardDeviationBetweenRates);

            outputValues = new Pair<>(outputAvgRate, outputStdDev);
        } catch (IOException | ParseException | SAXException | ParserConfigurationException | UnregisteredDateException e) {
            e.printStackTrace();
        }
        return outputValues;
    }

    private String prepareUrlToXml(LocalDate date) throws UnregisteredDateException {
        String xmlUrlPrefix = "http://www.nbp.pl/kursy/xml/";
        String xmlUrlSuffix = ".xml";

        String properXmlUrl = null;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String dataURLString = "http://www.nbp.pl/kursy/xml/dir" + (date.getYear() == currentYear ? "" : date.getYear()) + ".txt";
        String responseTableNamesData = restTemplate.getForEntity(dataURLString, String.class).getBody();
        String urlParsedDate = stringParserUtil.parseDateToUrlFormat(date);
        Pattern pattern = Pattern.compile("c[0-9]{0,3}z"+urlParsedDate);
        Matcher matcher = pattern.matcher(responseTableNamesData);
        if(matcher.find()){
            properXmlUrl = matcher.group(0);
        }
        String xmlUrlFragment = Optional.ofNullable(properXmlUrl).orElseThrow(() -> new UnregisteredDateException("Given date: " + date + " is not recorded in currency data!"));

        return xmlUrlPrefix + xmlUrlFragment + xmlUrlSuffix;
    }

    private BigDecimal acquireAvgRateForCurrency(String currency, ExchangeType xType, LocalDate date) throws IOException, SAXException, ParserConfigurationException, ParseException, UnregisteredDateException {
        String xmlUrl = prepareUrlToXml(date);
        ResponseEntity<String> response = restTemplate.getForEntity(xmlUrl, String.class);
        return xmlReaderUtils.acquireAvgRateFromXmlFile(currency, xType, response.getBody());
    }

    private BigDecimal computeAvgRate(BigDecimal... rates){
        BigDecimal sum = BigDecimal.valueOf(0);
        for(BigDecimal rate : rates){
            sum=sum.add(rate);
        }
        sum=sum.divide(BigDecimal.valueOf(rates.length), 4);
        return sum;
    }

    private BigDecimal computeStdDevBetweenRates(BigDecimal avgRate, BigDecimal... rates){
        BigDecimal sum = BigDecimal.valueOf(0);
        for(BigDecimal rate : rates) {
            sum=sum.add(avgRate.subtract(rate).pow(2));
        }
        BigDecimal meanOfDiffs = sum.divide(BigDecimal.valueOf(rates.length), 4);
        return BigDecimal.valueOf(Math.sqrt(meanOfDiffs.doubleValue())).setScale(4, RoundingMode.CEILING);
    }

    private enum Currencies {
        USD,
        EUR,
        CHF,
        GBP;

        public static boolean isValidCurrency(String currencyString){
            for(Currencies currency : values()){
                if(currency.name().equalsIgnoreCase(currencyString))
                    return true;
            }
            return false;
        }
    }
}
