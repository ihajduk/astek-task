package pl.parser.nbp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.parser.nbp.model.request.CurrencyStatsRequest;
import pl.parser.nbp.model.response.CurrencyStatsResponse;
import pl.parser.nbp.service.CurrencyRatesService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CurrencyRatesController {

    private CurrencyRatesService currencyRatesService;

    @Autowired
    public CurrencyRatesController(CurrencyRatesService currencyRatesService) {
        this.currencyRatesService = currencyRatesService;
    }

    @RequestMapping(value = "/rate-statistics", method = RequestMethod.GET)
    public CurrencyStatsResponse getRateStatistics(@Valid CurrencyStatsRequest currencyStatsRequest){
        return currencyRatesService.computeRatesStatistics(currencyStatsRequest);
    }
}
