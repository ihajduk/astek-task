package pl.parser.nbp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import pl.parser.nbp.model.ExchangeType;
import pl.parser.nbp.model.TableOfCurrencies;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.spi.Provider;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

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
            String output =
                    xType.equals(ExchangeType.BUY) ? filteredPosition.getBid()
                            : filteredPosition.getSell();

            // TODO: czy wydzielać metody oraz wzorzec strategia na koniec
            return parseUtils.parseValueToCurrency(output);

        } catch (JAXBException ex) {
            throw new RuntimeException("Could not parse XML: ", ex);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("XML parsing could not be completed");
    }
}