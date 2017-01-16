package pl.parser.nbp.model;

public class RatesStatistics {

    private String avgBuyingRate;
    private String stdDevSellingRate;

    public RatesStatistics(String avgBuyingRate, String stdDevSellingRate) {
        this.avgBuyingRate = avgBuyingRate;
        this.stdDevSellingRate = stdDevSellingRate;
    }

    @Override
    public String toString(){
        return avgBuyingRate+"\n"+stdDevSellingRate;
    }
}
