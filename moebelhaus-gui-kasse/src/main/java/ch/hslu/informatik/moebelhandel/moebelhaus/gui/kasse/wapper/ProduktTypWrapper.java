package ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse.wapper;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;

public class ProduktTypWrapper {
	
	private ProduktTyp produktTyp;
	private int anzahlInt;
	private int anzahlExt;

	public ProduktTypWrapper(ProduktTyp produktTyp, int anzahlInt, int anzahlExt) {
		this.produktTyp = produktTyp;
		this.anzahlInt = anzahlInt;
		this.anzahlExt = anzahlExt;
	}

	public String getProdukteName () {
		return produktTyp.getName();
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
