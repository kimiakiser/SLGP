package ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse.wapper;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktRuecknahme;

public class ProduktRuecknahmeWrapper {
	
	private int nummer;
	private ProduktRuecknahme produktRuecknahme;

	public ProduktRuecknahmeWrapper(int nummer, ProduktRuecknahme produktRuecknahme) {
		this.nummer = nummer;
		this.produktRuecknahme = produktRuecknahme;
	}

	public int getNummer() {
		return nummer;
	}

	public void setNummer(int nummer) {
		this.nummer = nummer;
	}
	
	public long getRechnungsNummer () {
		return produktRuecknahme.getRechnung().getId();
	}
	
	public String getProduktTypCode () {
		return produktRuecknahme.getProduktTyp().getTypCode();
	}
	
	public int getAnzahl () {
		return produktRuecknahme.getAnzahl();
	}
	
	public String getBemerkung () {
		return produktRuecknahme.getBemerkung();
	}

	public ProduktRuecknahme getProduktRuecknahme() {
		return produktRuecknahme;
	}

	public void setProduktRuecknahme(ProduktRuecknahme produktRuecknahme) {
		this.produktRuecknahme = produktRuecknahme;
	}
}
