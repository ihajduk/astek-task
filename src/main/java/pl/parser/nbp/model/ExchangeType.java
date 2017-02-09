package pl.parser.nbp.model;

public enum ExchangeType {
    BUY {
        @Override
        public String getRateValue(TableOfCurrencies.Position position) {
            return position.getBid();
        }
    },
    SELL{
        @Override
        public String getRateValue(TableOfCurrencies.Position position) {
            return position.getSell();
        }
    };

    public abstract String getRateValue(TableOfCurrencies.Position position);
}
