package ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager.wrapper;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lieferant;

public class LieferantenWrapper {

    private int nummer;

    private Lieferant lieferant;

    public LieferantenWrapper(int nummer, Lieferant lieferant) {
        this.nummer = nummer;
        this.lieferant = lieferant;
    }

    public int getNummer() {
        return nummer;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }

    public String getName() {
        return lieferant.getName();
    }

    public String getStrasse() {
        return lieferant.getAdresse().getStrasse();
    }

    public int getPlz() {
        return lieferant.getAdresse().getPlz();
    }

    public String getOrt() {
        return lieferant.getAdresse().getOrt();
    }

    public String getEmail() {
        return lieferant.getKontakt().getEmail();
    }

    public String getTelefon() {
        return lieferant.getKontakt().getTelefon();
    }

    public Lieferant getLieferant() {
        return lieferant;
    }

    public void setLieferant(Lieferant lieferant) {
        this.lieferant = lieferant;
    }

}
