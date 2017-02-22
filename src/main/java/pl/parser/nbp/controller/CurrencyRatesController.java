package pl.parser.nbp.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.parser.nbp.model.request.CurrencyStatsRequest;
import pl.parser.nbp.model.response.CurrencyStatsResponse;
import pl.parser.nbp.model.response.ErrorResponse;
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

    @ApiOperation(value = "get rates statistics")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currencyCode", value = "Allowed codes: (EUR/GBP/CHF/USD)", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "startDateString", value = "Format: yyyy-MM-dd", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endDateString", value = "Format: yyyy-MM-dd", required = true, dataType = "string", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = CurrencyStatsResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(value = "/rate-statistics", method = RequestMethod.GET)
    public CurrencyStatsResponse getRateStatistics(@Valid CurrencyStatsRequest currencyStatsRequest) {
        return currencyRatesService.computeRatesStatistics(currencyStatsRequest);
    }
}
