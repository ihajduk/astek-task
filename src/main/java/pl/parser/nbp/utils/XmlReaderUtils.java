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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;

@Component
public class XmlReaderUtils {

    private static final String POSITION_TAG_NAME = "pozycja";
    private static final String CURRENCY_CODE_TAG_NAME = "kod_waluty";
    private static final String BUYING_RATE = "kurs_kupna";
    private static final String SELLING_RATE = "kurs_sprzedazy";
    private final DocumentBuilder db;

    private final ParseUtils parseUtils;

    @Autowired
    public XmlReaderUtils(ParseUtils parseUtils) throws ParserConfigurationException {
        this.parseUtils = parseUtils;
        db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    public BigDecimal acquireAvgRateFromXmlFile(String currency, ExchangeType xType, String body) {
        try{
        Document dom = db.parse(new InputSource(new ByteArrayInputStream(body.getBytes("UTF-8"))));
        NodeList nl = dom.getElementsByTagName(POSITION_TAG_NAME);
        if (nl != null) {
            int length = nl.getLength();
            for (int i = 0; i < length; i++) {
                if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) nl.item(i);
                    if(currency.equals(el.getElementsByTagName(CURRENCY_CODE_TAG_NAME).item(0).getTextContent())){
                        String avgCurrency = el.getElementsByTagName(resolveExchangeType(xType)
                        ).item(0).getTextContent();
                        return parseUtils.parseValueToCurrency(avgCurrency);
                    }
                }
            }
        }
        } catch (ParseException | SAXException | IOException ex) {
            throw  new RuntimeException("Could not parse XML: ", ex);
        }
        throw new RuntimeException("XML parsing could not be completed");
    }

    private String resolveExchangeType(ExchangeType xType) {
        return xType.equals(ExchangeType.BUY)
                ? BUYING_RATE
                : SELLING_RATE;
    }
}