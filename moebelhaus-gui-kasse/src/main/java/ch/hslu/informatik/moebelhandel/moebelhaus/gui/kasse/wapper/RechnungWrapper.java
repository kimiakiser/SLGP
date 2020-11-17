package ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse.wapper;

import java.text.SimpleDateFormat;
import java.util.List;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Rechnung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.RechnungPosition;

public class RechnungWrapper {
	
    private int nr;
    private Rechnung rechnung;

    public RechnungWrapper(int nr, Rechnung rechnung) {
        this.nr = nr;
        this.rechnung = rechnung;
    }

	public int getNr() {
		return nr;
	}

	public void setNr(int nr) {
		this.nr = nr;
	}
	
	public long getId() {
		return rechnung.getId();
	}
	
	public String getDatum() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy ' um ' HH:mm:ss");
		return sdf.format(rechnung.getZeit().getTime()); 
	}
	
	public String getBenutzer() {
		return rechnung.getBenutzer().getVorname() + " " + rechnung.getBenutzer().getNachname();
	}
	
	public double getBetrag() {
		return rechnung.getBetrag();
	}
	
	public int getAnzahlProdukte() {
		int anzahl = 0;
		for (RechnungPosition rpos : rechnung.getRechnungPositionListe()) {
			anzahl = anzahl + rpos.getAnzahl();
		}
		return anzahl;
	}
	
	public List<RechnungPosition> getRechnungPositionListe() {
		return rechnung.getRechnungPositionListe();
	}

	public Rechnung getRechnung() {
		return rechnung;
	}

	public void setRechnung(Rechnung rechnung) {
		this.rechnung = rechnung;
	}
}
