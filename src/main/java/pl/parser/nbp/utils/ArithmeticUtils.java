package pl.parser.nbp.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

@Component
public class ArithmeticUtils {

    private final int precision;

    public ArithmeticUtils(@Value("${currency.precision}") int precision) {
        this.precision = precision;
    }

    public BigDecimal computeAvgRate(BigDecimal... rates){
        return Arrays.stream(rates)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(rates.length), precision);
    }

    public BigDecimal computeStdDevRate(BigDecimal avgRate, BigDecimal... rates){
        BigDecimal sum = Arrays.stream(rates)
                .map(rate -> avgRate.subtract(rate).pow(2))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(rates.length), 4);

        return BigDecimal.valueOf(Math.sqrt(sum.doubleValue())).setScale(4, RoundingMode.HALF_EVEN);
    }
}
