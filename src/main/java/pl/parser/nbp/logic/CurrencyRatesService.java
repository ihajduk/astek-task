package pl.parser.nbp.logic;

import javafx.util.Pair;
import org.xml.sax.SAXException;
import pl.parser.nbp.exceptions.UnregisteredDateException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by iwha on 1/14/2017.
 */
public interface CurrencyRatesService {
    Pair<String, String> computeEntryValues(String currency, String startDateString, String endDateString);
}
