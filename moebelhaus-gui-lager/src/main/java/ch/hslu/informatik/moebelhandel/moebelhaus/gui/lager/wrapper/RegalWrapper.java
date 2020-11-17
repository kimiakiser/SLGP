package ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager.wrapper;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Regal;

public class RegalWrapper {

    private int nummer;
    private Regal regal;;

    public RegalWrapper(int nummer, Regal regal) {
        this.nummer = nummer;
        this.regal = regal;
    }

    public int getNummer() {
        return nummer;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }

    public String getBezeichnung() {
        return regal.getBezeichnung();
    }

    public int getAnzahlTablare() {
        return regal.getAnzahlTablare();
    }

    public Regal getRegal() {
        return regal;
    }

}
