package ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager.wrapper;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;

public class BenutzerWrapper {

    private int nummer;
    private Benutzer benutzer;

    public BenutzerWrapper(int nummer, Benutzer benutzer) {
        this.nummer = nummer;
        this.benutzer = benutzer;
    }

    public String getName() {
        return benutzer.getNachname();
    }

    public String getVorname() {
        return benutzer.getVorname();
    }

    public String getStrasse() {
        return benutzer.getAdresse().getStrasse();
    }

    public int getPlz() {
        return benutzer.getAdresse().getPlz();
    }

    public String getOrt() {
        return benutzer.getAdresse().getOrt();
    }

    public String getEmail() {
        return benutzer.getKontakt().getEmail();
    }

    public String getTelefon() {
        return benutzer.getKontakt().getTelefon();
    }

    public String getBenutzername() {
        return benutzer.getCredentials().getBenutzername();
    }

    public String getKennwort() {
        return benutzer.getCredentials().getPasswort();
    }

    public String getRolle() {
        return benutzer.getRolle().toString();
    }

    public int getNummer() {
        return nummer;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }

    public Benutzer getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(Benutzer benutzer) {
        this.benutzer = benutzer;
    }

}
