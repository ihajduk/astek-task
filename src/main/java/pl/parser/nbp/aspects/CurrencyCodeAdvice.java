package pl.parser.nbp.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CurrencyCodeAdvice {

    //TODO: to się opierdoli aspektami. Każda metoda która będzie miała parametr typu currencyCode, będzie miała toUpperCase

//    @Before(value = "pl.parser.nbp.CurrencyRatesService.computeRatesStatistics(String currencyCode, String startDateString, String endDateString)", argNames = "currencyCode")
    public void currencyCodeToUpperCase(String currencyCode){

    }

}
