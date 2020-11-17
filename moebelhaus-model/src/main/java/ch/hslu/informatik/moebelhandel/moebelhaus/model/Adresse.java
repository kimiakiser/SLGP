package ch.hslu.informatik.moebelhandel.moebelhaus.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Adresse implements Serializable {

	private static final long serialVersionUID = -4638187264975728996L;

	private String strasse;
	private int plz;
	private String ort;

	public Adresse() {

	}

	public Adresse(String strasse, int plz, String name) {
		this.strasse = strasse;
		this.plz = plz;
		this.ort = name;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public int getPlz() {
		return plz;
	}

	public void setPlz(int plz) {
		this.plz = plz;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ort == null) ? 0 : ort.hashCode());
		result = prime * result + plz;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adresse other = (Adresse) obj;
		if (ort == null) {
			if (other.ort != null)
				return false;
		} else if (!ort.equals(other.ort))
			return false;
		if (plz != other.plz)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Adresse [strasse=" + strasse + ", plz=" + plz + ", ort=" + ort + "]";
	}

}
