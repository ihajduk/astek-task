package pl.parser.nbp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.parser.nbp.model.ExchangeType;
import pl.parser.nbp.model.TableOfCurrencies;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Component
public class XmlReaderUtils {

    private final ParseUtils parseUtils;

    @Autowired
    public XmlReaderUtils(ParseUtils parseUtils) throws ParserConfigurationException {
        this.parseUtils = parseUtils;
    }

    public BigDecimal acquireAvgRateFromXmlFile(String currency, ExchangeType xType, String body) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(TableOfCurrencies.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(body);
            TableOfCurrencies tableOfCurrencies = (TableOfCurrencies) jaxbUnmarshaller.unmarshal(reader);
            List<TableOfCurrencies.Position> positions = tableOfCurrencies.getPositions();
            Optional<TableOfCurrencies.Position> currencyTable = positions.stream().filter(t -> t.getCurrencyCode().equals(currency)).findFirst();
            TableOfCurrencies.Position filteredPosition = currencyTable.orElseThrow(() -> new JAXBException("Value for given currency is null - possibly XML document has changed"));
            String outputAvgRate = xType.getRateValue(filteredPosition);
            return parseUtils.parseValueToCurrency(outputAvgRate);
        } catch (JAXBException ex) {
            throw new RuntimeException("Could not parse XML: ", ex);
        } catch (ParseException ex) {
            throw new RuntimeException("Could not parse currency: ", ex);
        }
    }


}