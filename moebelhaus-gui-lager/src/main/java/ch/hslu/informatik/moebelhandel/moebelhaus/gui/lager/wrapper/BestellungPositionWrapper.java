package ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager.wrapper;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.BestellungPosition;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;

public class BestellungPositionWrapper {

    private int nummer;

    private BestellungPosition bestellungPosition;

    public BestellungPositionWrapper(int nummer, BestellungPosition bestellungPosition) {
        this.nummer = nummer;
        this.bestellungPosition = bestellungPosition;
    }

    public String getLieferant() {
        return bestellungPosition.getProduktTyp().getLieferant().getName();
    }

    public ProduktTyp getProduktTyp() {
        return bestellungPosition.getProduktTyp();
    }

    public int getAnzahl() {
        return bestellungPosition.getAnzahl();
    }

    public String getProduktname() {
        return bestellungPosition.getProduktTyp().getName();
    }

    public String getProduktCode() {
        return bestellungPosition.getProduktTyp().getTypCode();
    }

    public int getNummer() {
        return nummer;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }

    public BestellungPosition getBestellungPosition() {
        return bestellungPosition;
    }

    public void setBestellungPosition(BestellungPosition bestellungPosition) {
        this.bestellungPosition = bestellungPosition;
    }

}
