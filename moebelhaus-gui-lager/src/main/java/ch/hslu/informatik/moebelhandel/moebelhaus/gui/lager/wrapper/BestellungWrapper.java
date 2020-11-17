package ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager.wrapper;

import java.text.SimpleDateFormat;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Bestellung;

public class BestellungWrapper {
	
	private int nummer;

    private Bestellung bestellung;

    public BestellungWrapper(int nummer, Bestellung bestellung) {
        this.nummer = nummer;
        this.bestellung = bestellung;
    }

	public int getNummer() {
		return nummer;
	}

	public void setNummer(int nummer) {
		this.nummer = nummer;
	}

	public Bestellung getBestellung() {
		return bestellung;
	}

	public void setBestellung(Bestellung bestellung) {
		this.bestellung = bestellung;
	}
	
	public long getId () {
		return bestellung.getId();
	}
	
	public String getBesteller() {
		return bestellung.getBesteller().getName();
	}
	
	public String getLieferantBestellung() {
		return bestellung.getBestellungPositionListe().get(0).getProduktTyp().getLieferant().getName();
	}
	
	public String getDatum() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		return sdf.format(bestellung.getDatum().getTime()); 
	}
}