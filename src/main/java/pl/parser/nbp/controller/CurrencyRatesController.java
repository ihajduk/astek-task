package pl.parser.nbp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.parser.nbp.model.RatesStatistics;
import pl.parser.nbp.service.CurrencyRatesService;

@RestController
@RequestMapping("/api")
public class CurrencyRatesController {

    private CurrencyRatesService currencyRatesService;

    @Autowired
    public CurrencyRatesController(CurrencyRatesService currencyRatesService) {
        this.currencyRatesService = currencyRatesService;
    }

    @RequestMapping(value = "/rate-statistics", method = RequestMethod.GET)
    public RatesStatistics getRateStatistics(String currencyCode, String startDateString, String endDateString){
        return currencyRatesService.computeRatesStatistics(currencyCode, startDateString, endDateString);
    }
}
