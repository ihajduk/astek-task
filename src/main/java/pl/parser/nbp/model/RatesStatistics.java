package pl.parser.nbp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RatesStatistics {
    private String avgBuyingRate;
    private String stdDevSellingRate;
}
