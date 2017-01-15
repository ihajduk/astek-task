package pl.parser.nbp.utils;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import pl.parser.nbp.logic.ExchangeType;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;

/**
 * Created by iwha on 1/15/2017.
 */
@Component
public class XmlReaderUtils {

    private StringParserUtil stringParserUtil = new StringParserUtil();

    public BigDecimal acquireAvgRateFromXmlFile(String currency, ExchangeType xType, String body) throws ParserConfigurationException, IOException, SAXException, ParseException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document dom = db.parse(new InputSource(new ByteArrayInputStream(body.getBytes("UTF-8"))));
        NodeList nl = dom.getElementsByTagName("pozycja");
        if (nl != null) {
            int length = nl.getLength();
            for (int i = 0; i < length; i++) {
                if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) nl.item(i);
                    if(currency.equals(el.getElementsByTagName("kod_waluty").item(0).getTextContent())){
                        String avgCurrency = el.getElementsByTagName(
                                (xType.equals(ExchangeType.BUY)
                                        ? "kurs_kupna"
                                        : "kurs_sprzedazy")
                        ).item(0).getTextContent();
                        return stringParserUtil.parseValueToCurrency(avgCurrency);
                    }
                }
            }
        }

        return null;
    }
}