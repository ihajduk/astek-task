package pl.parser.nbp.model;

import lombok.Getter;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "tabela_kursow")
@XmlAccessorType(XmlAccessType.FIELD)
public class TableOfCurrencies {

    @XmlAttribute(name = "typ")
    String type;
    @XmlAttribute(name = "uid")
    String uid;

    @XmlElement(name = "pozycja")
    private List<Position> positions;

    public List<Position> getPositions() {
        return positions;
    }

    @Getter
    public static class Position {
        @XmlElement(name = "nazwa_waluty")
        private String currencyName;
        @XmlElement(name = "przelicznik")
        private String multiplier;
        @XmlElement(name = "kod_waluty")
        private String currencyCode;
        @XmlElement(name = "kurs_kupna")
        private String bid;
        @XmlElement(name = "kurs_sprzedazy")
        private String sell;
    }
}
