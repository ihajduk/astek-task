package pl.parser.nbp.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArithmeticUtilsTest {

    @Autowired
    private ArithmeticUtils arithmeticUtils;

    @Test
    public void shouldComputeAvgRate() throws Exception {
        //given
        BigDecimal[] rates = {BigDecimal.valueOf(4.1301), BigDecimal.valueOf(4.1569)};
        //when
        BigDecimal output = arithmeticUtils.computeAvgRate(rates);
        //then
        Assert.assertEquals(BigDecimal.valueOf(4.1435), output);
    }

    @Test
    public void shouldComputeStdDevRate() throws Exception {
        //given
        BigDecimal avgRate = BigDecimal.valueOf(4.2272);
        BigDecimal[] rates = {BigDecimal.valueOf(4.2135), BigDecimal.valueOf(4.2409)};
        //when
        BigDecimal output = arithmeticUtils.computeStdDevRate(avgRate, rates);
        //then
        Assert.assertEquals(BigDecimal.valueOf(0.0137), output);
    }
}