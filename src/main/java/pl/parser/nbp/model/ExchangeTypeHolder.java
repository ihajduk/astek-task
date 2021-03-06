package pl.parser.nbp.model;

import pl.parser.nbp.model.xml.TableOfCurrencies;

public enum ExchangeTypeHolder {
    BUY {
        @Override
        public String getExchangeValue(TableOfCurrencies.Position position) {
            return position.getBid();
        }
    },
    SELL{
        @Override
        public String getExchangeValue(TableOfCurrencies.Position position) {
            return position.getSell();
        }
    };

    public abstract String getExchangeValue(TableOfCurrencies.Position position);
}
