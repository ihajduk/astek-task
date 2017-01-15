package pl.parser.nbp;

import javafx.util.Pair;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.xml.sax.SAXException;
import pl.parser.nbp.exceptions.UnregisteredDateException;
import pl.parser.nbp.logic.CurrencyRatesService;
import pl.parser.nbp.logic.CurrencyRatesServiceImpl;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by iwha on 1/14/2017.
 */
public class MainClass {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, ParseException, UnregisteredDateException {
        String currency = "EUR";
        String startDate = "2013-01-28";
        String endDate = "2013-01-31";

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SpringConfig.class);
        context.refresh();

        CurrencyRatesService currencyRatesService = context.getBean(CurrencyRatesServiceImpl.class);
        Pair<String, String> stringStringPair = currencyRatesService.computeEntryValues(currency, startDate, endDate);
        System.out.println(stringStringPair.getKey()+"\n"+stringStringPair.getValue());
    }
}
