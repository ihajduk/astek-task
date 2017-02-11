package pl.parser.nbp.model;

public class RatesStatistics {

    private String avgBuyingRate;
    private String stdDevSellingRate;

    public String getAvgBuyingRate() {
        return avgBuyingRate;
    }

    public String getStdDevSellingRate() {
        return stdDevSellingRate;
    }

    public RatesStatistics(String avgBuyingRate, String stdDevSellingRate) {
        this.avgBuyingRate = avgBuyingRate;
        this.stdDevSellingRate = stdDevSellingRate;
    }
}
