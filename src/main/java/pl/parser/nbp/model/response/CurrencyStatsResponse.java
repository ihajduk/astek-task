package pl.parser.nbp.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CurrencyStatsResponse {
    private String avgBuyingRate;
    private String stdDevSellingRate;
}
