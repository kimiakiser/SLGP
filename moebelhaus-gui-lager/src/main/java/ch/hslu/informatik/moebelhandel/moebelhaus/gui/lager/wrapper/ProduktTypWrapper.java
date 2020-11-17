package ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager.wrapper;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;

public class ProduktTypWrapper {

	private int nr;
	private ProduktTyp produktTyp;
	private int anzahlInt;
	private int anzahlExt;

	public ProduktTypWrapper(int nr, ProduktTyp produktTyp, int anzahlInt, int anzahlExt) {
		this.nr = nr;
		this.produktTyp = produktTyp;
		this.anzahlInt = anzahlInt;
		this.anzahlExt = anzahlExt;
	}

	public int getNr() {
		return nr;
	}

	public void setNr(int nr) {
		this.nr = nr;
	}

	public String getLieferantName() {
		return produktTyp.getLieferant().getName();
	}

	public String getProduktTypCode() {
		return produktTyp.getTypCode();
	}

	public String getBeschreibung() {
		return produktTyp.getBeschreibung();
	}

	public String getLieferant() {
		return produktTyp.getLieferant().getName();
	}

	public double getPreis() {
		return produktTyp.getPreis();
	}

	public double getMinBestand() {
		return produktTyp.getMinimalerBestand();
	}

	public int getMaxBestand() {
		return produktTyp.getMaximalerBestand();
	}

	public String getAblage() {
		return produktTyp.getAblage();
	}

	public int getAnzahlInt() {
		return anzahlInt;
	}

	public void setAnzahlInt(int anzahlInt) {
		this.anzahlInt = anzahlInt;
	}
	
	public int getAnzahlExt() {
		return anzahlExt;
	}

	public void setAnzahlExt(int anzahlExt) {
		this.anzahlExt = anzahlExt;
	}
	
	public ProduktTyp getProduktTyp() {
		return produktTyp;
	}
}