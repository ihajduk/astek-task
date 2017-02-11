package pl.parser.nbp.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.parser.nbp.SpringConfig;
import pl.parser.nbp.model.ExchangeTypeHolder;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class XmlReaderUtilsTest {

    @Autowired
    XmlReaderUtils xmlReaderUtils;

    @Test
    public void shouldAcquireAvgRateFromXmlBody() throws IOException {
        //given
        String currencyCode = "EUR";
        ExchangeTypeHolder xTypeHolder = ExchangeTypeHolder.BUY;
        String xmlBodyFilePath = "src/test/resources/table-of-currencies-body";
        String xmlBody = new String(Files.readAllBytes(Paths.get(xmlBodyFilePath)));
        //when
        BigDecimal avgRate = xmlReaderUtils.acquireAvgRateFromXmlBody(currencyCode, xTypeHolder, xmlBody);
        //then
        Assert.assertEquals(avgRate, BigDecimal.valueOf(4.1397));
    }
}