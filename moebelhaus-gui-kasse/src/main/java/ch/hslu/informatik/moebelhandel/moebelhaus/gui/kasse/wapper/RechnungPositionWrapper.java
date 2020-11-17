package ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse.wapper;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.RechnungPosition;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;

public class RechnungPositionWrapper {

    private int nummer;

    private RechnungPosition rechnungPosition;

    public RechnungPositionWrapper(int nummer, RechnungPosition rechnungPosition) {
        this.nummer = nummer;
        this.rechnungPosition = rechnungPosition;
    }

    public String getLieferant() {
        return rechnungPosition.getProduktTyp().getLieferant().getName();
    }

    public ProduktTyp getProduktTyp() {
        return rechnungPosition.getProduktTyp();
    }

    public int getAnzahl() {
        return rechnungPosition.getAnzahl();
    }

    public String getProduktname() {
        return rechnungPosition.getProduktTyp().getName();
    }

    public String getProduktCode() {
        return rechnungPosition.getProduktTyp().getTypCode();
    }
    
    public String getBeschreibung() {
    	return rechnungPosition.getProduktTyp().getBeschreibung();
    }
    
    public double getPreis() {
    	return rechnungPosition.getProduktTyp().getPreis();
    }

    public int getNummer() {
        return nummer;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }

    public RechnungPosition getRechnungPosition() {
        return rechnungPosition;
    }

    public void setRechnungPosition(RechnungPosition rechnungPosition) {
        this.rechnungPosition = rechnungPosition;
    }
}