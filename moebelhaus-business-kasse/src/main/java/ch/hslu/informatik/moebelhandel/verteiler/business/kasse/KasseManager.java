package ch.hslu.informatik.moebelhandel.verteiler.business.kasse;

import java.util.GregorianCalendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.api.KasseService;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Produkt;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktRuecknahme;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Rechnung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.RechnungPosition;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.ProduktDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.ProduktRuecknahmeDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.ProduktTypDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.RechnungDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.ProduktDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.ProduktRuecknahmeDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.ProduktTypDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.RechnungDAOImpl;

public class KasseManager implements KasseService {

	private static Logger logger = LogManager.getLogger(KasseManager.class);

	private ProduktDAO produktDAO;
	private ProduktTypDAO produktTypDAO;
	private ProduktRuecknahmeDAO produktRuecknahmeDAO;
	private RechnungDAO rechnungDAO;

	public ProduktDAO getProduktDAO() {

		if (produktDAO == null) {
			produktDAO = new ProduktDAOImpl();
		}

		return produktDAO;
	}

	public ProduktTypDAO getProduktTypDAO() {

		if (produktTypDAO == null) {
			produktTypDAO = new ProduktTypDAOImpl();
		}

		return produktTypDAO;
	}

	public ProduktRuecknahmeDAO getProduktRuecknahmeDAO() {

		if (produktRuecknahmeDAO == null) {
			produktRuecknahmeDAO = new ProduktRuecknahmeDAOImpl();
		}

		return produktRuecknahmeDAO;
	}

	public RechnungDAO getRechnungDAO() {

		if (rechnungDAO == null) {
			rechnungDAO = new RechnungDAOImpl();
		}

		return rechnungDAO;
	}

	public Rechnung verkaufen(List<Produkt> produktListe, Benutzer benutzer) throws Exception {

		if (produktListe.isEmpty()) {
			return null;
		}

		Rechnung rechnung = new Rechnung(new GregorianCalendar(), benutzer);

		ProduktTyp produktTyp = null;

		for (Produkt produkt : produktListe) {
			produktTyp = produkt.getTyp();

			/*
			 * Kontrollieren, ob die Rechnung eine RechnungPosition für den
			 * gleichen Typ bereits enthält oder nicht. Falls ja, wird nur die
			 * Anzahl inkrementiert, sonst wird eine neue RechnungPosition
			 * erstellt.
			 */
			RechnungPosition pos = rechnung.findByProduktTypCode(produktTyp.getTypCode());

			if (pos != null) {
				int anzahl = pos.getAnzahl();
				pos.setAnzahl(anzahl + 1);
			} else {
				pos = new RechnungPosition(produktTyp, 1);
				rechnung.addRechnungPosition(pos);
			}
		}

		/* Rechnung 'speichern' */
		new RechnungDAOImpl().save(rechnung);

		/* Produkte aus dem Lager entfernen */
		for (Produkt p : produktListe) {
			p.setVerkauft(true);
			getProduktDAO().update(p);

			logger.info("Produkt mit ProduktCode=" + p.getCode() + " wurde Verkauft");
		}

		return rechnung;
	}

	public void produktZuruecknehmen(ProduktRuecknahme produktRuecknahme) throws Exception {

		try {

			/* Rücknahme speichern */
			getProduktRuecknahmeDAO().save(produktRuecknahme);

		} catch (Exception e) {
			String msg = "Rücknahme misslungen";
			logger.error(msg, e);
			throw new Exception(msg);
		}
	}

	public Rechnung findByRechnungNummer(long nummer) throws Exception {

		try {
			return getRechnungDAO().findById(nummer);
		} catch (Exception e) {
			String msg = "Suche nach der Rechnung mit Nr. \'" + nummer + "\n misslungen";
			logger.error(msg, e);
			throw new Exception(msg);
		}
	}

	public ProduktTyp findByProduktTypCode(String produktTypCode) throws Exception {
		try {
			return getProduktTypDAO().findByTypCode(produktTypCode);
		} catch (Exception e) {
			String msg = "Suche nach Produkt-Typ mit TypCode \'" + produktTypCode + "\' misslungen";
			logger.error(msg, e);
			throw new Exception(msg);
		}
	}

	public Produkt findByProduktCode(long produktCode) throws Exception {

		try {
			return getProduktDAO().findByCode(produktCode);
		} catch (Exception e) {
			String msg = "Suche nach dem Produkt mit Code \'" + produktCode + "\' misslungen";
			logger.error(msg, e);
			throw new Exception(msg);
		}
	}

	public List<Rechnung> findByBenutzerUndDatum(Benutzer benutzer, GregorianCalendar datum) throws Exception {
		try {
			return getRechnungDAO().findByBenutzerUndDatum(benutzer, datum);
		} catch (Exception e) {
			String msg = "Rechnungen des Benutzers " + benutzer.getNachname() + " " + benutzer.getVorname()
					+ " konnten nicht geholt werden";
			logger.error(msg, e);
			throw new Exception(msg);
		}
	}

	@Override
	public List<Produkt> findAlleAmLagerVerfuegbareProdukte() throws Exception {
		try {
			return getProduktDAO().findAll();
		} catch (Exception e) {
			String msg = "Hollen aller am Lager verfügbaren Produkte misslungen";
			logger.error(msg, e);
			throw new Exception(msg);
		}
	}

}
