package pl.parser.nbp.model;

public enum Currencies {
    USD,
    EUR,
    CHF,
    GBP;

    public static boolean isValidCurrency(String currencyString){
        for(Currencies currency : values()){
            if(currency.name().equals(currencyString))
                return true;
        }
        return false;
    }
}
