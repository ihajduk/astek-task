package pl.parser.nbp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.parser.nbp.model.ExchangeTypeHolder;
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
    private final Unmarshaller jaxbUnmarshaller;

    @Autowired
    public XmlReaderUtils(ParseUtils parseUtils) throws ParserConfigurationException, JAXBException {
        this.parseUtils = parseUtils;
        JAXBContext jaxbContext = JAXBContext.newInstance(TableOfCurrencies.class);
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    }

    public BigDecimal acquireAvgRateFromXmlBody(String currencyCode, ExchangeTypeHolder xType, String body) {
        try {
            TableOfCurrencies tableOfCurrencies = unmarshalTableOfCurrencies(body);
            List<TableOfCurrencies.Position> positions = tableOfCurrencies.getPositions();
            Optional<TableOfCurrencies.Position> currencyTable = positions.stream()
                    .filter(t -> t.getCurrencyCode().equals(currencyCode))
                    .findFirst();
            TableOfCurrencies.Position filteredPosition = currencyTable.orElseThrow(
                    () -> new JAXBException("Value for given currencyCode is null - possibly XML document has changed"));
            String outputAvgRate = xType.getExchangeValue(filteredPosition);
            return parseUtils.parseStringToCurrency(outputAvgRate);
        } catch (JAXBException ex) {
            throw new RuntimeException("Could not parse XML: ", ex);
        } catch (ParseException ex) {
            throw new RuntimeException("Could not parse currencyCode: ", ex);
        }
    }

    private TableOfCurrencies unmarshalTableOfCurrencies(String body) throws JAXBException {
        StringReader reader = new StringReader(body);
        return (TableOfCurrencies) jaxbUnmarshaller.unmarshal(reader);
    }
}