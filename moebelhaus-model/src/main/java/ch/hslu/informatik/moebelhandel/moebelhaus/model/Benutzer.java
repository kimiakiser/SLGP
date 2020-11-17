package ch.hslu.informatik.moebelhandel.moebelhaus.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
		@NamedQuery(name = "Benutzer.findByBenutzername", query = "SELECT e FROM Benutzer e WHERE e.credentials.benutzername=:benutzername"),
		@NamedQuery(name = "Benutzer.findByRolleTyp", query = "SELECT e FROM Benutzer e WHERE e.rolle=:rolleTyp") })
public class Benutzer extends Person {

	private static final long serialVersionUID = 2929748139540837451L;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Credentials credentials;

	@Enumerated(EnumType.STRING)
	private RolleTyp rolle;

	public Benutzer() {

	}

	public Benutzer(String nachname, String vorname, Adresse adresse, Kontakt kontakt, Credentials credentials,
			RolleTyp rolle) {
		super(nachname, vorname, adresse, kontakt);
		this.credentials = credentials;
		this.rolle = rolle;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public RolleTyp getRolle() {
		return rolle;
	}

	public void setRolle(RolleTyp rolle) {
		this.rolle = rolle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((credentials == null) ? 0 : credentials.hashCode());
		result = prime * result + ((rolle == null) ? 0 : rolle.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Benutzer other = (Benutzer) obj;
		if (credentials == null) {
			if (other.credentials != null)
				return false;
		} else if (!credentials.equals(other.credentials))
			return false;
		if (rolle != other.rolle)
			return false;
		return true;
	}

}
