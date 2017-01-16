package pl.parser.nbp;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.parser.nbp.model.RatesStatistics;
import pl.parser.nbp.service.CurrencyRatesService;
import pl.parser.nbp.service.CurrencyRatesServiceImpl;

public class MainClass {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        CurrencyRatesService currencyRatesService = context.getBean(CurrencyRatesServiceImpl.class);

        String currency = args[0];
        String startDate = args[1];
        String endDate = args[2];

        RatesStatistics rateStatistics = currencyRatesService.computeRatesStatistics(currency, startDate, endDate);
        System.out.println(rateStatistics);
    }
}
