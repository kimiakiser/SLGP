package ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager.wrapper;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.RuecksendungPosition;

public class RuecksendungPositionWrapper {

    private int nummer;
    private RuecksendungPosition ruecksendungPosition;

    public RuecksendungPositionWrapper(int nummer, RuecksendungPosition ruecksendungPosition) {
        this.nummer = nummer;
        this.ruecksendungPosition = ruecksendungPosition;
    }

    public int getNummer() {
        return nummer;
    }

    public ProduktTyp getProduktTyp() {
        return ruecksendungPosition.getProduktTyp();
    }

    public String getProduktTypCode() {
        return ruecksendungPosition.getProduktTyp().getTypCode();
    }

    public int getAnzahl() {
        return ruecksendungPosition.getAnzahl();
    }

    public String getBemerkung() {
        return ruecksendungPosition.getBemerkung();
    }

    public RuecksendungPosition getRuecksendungPosition() {
        return ruecksendungPosition;
    }
}
