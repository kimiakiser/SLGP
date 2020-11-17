package ch.hslu.informatik.moebelhandel.moebelhaus.business.initializer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaderJDOMFactory;
import org.jdom2.input.sax.XMLReaderSchemaFactory;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Adresse;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Credentials;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Kontakt;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lager;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lieferant;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Moebelhaus;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Produkt;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Regal;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.RolleTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Tablar;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.BenutzerDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.LagerDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.LieferantDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.ProduktDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.ProduktTypDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.RegalDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.TablarDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.BenutzerDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.LagerDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.LieferantDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.MoebelhausDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.ProduktDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.ProduktTypDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.RegalDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.TablarDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.JPAUtil;

public class AppInitializer {

	private static Logger logger = LogManager.getLogger(AppInitializer.class);
	private static Map<String, Lieferant> lieferantenMap = new HashMap<String, Lieferant>();

	public static void main(String[] args) {

		try {

			dropTables();
			logger.info(">> DATENBANK SCHEMA NEU ERSTELLT");

			initApplication();
			initTestdata();

			logger.info(">> DATENBANK ERFOLGREICH INITIALISIERT");

		} catch (Exception e) {
			logger.error("Initialisierung misslungen", e);
			throw new RuntimeException(e);
		}
	}

	private static void dropTables() {

		/*
		 * Es wird nur die Verbindung aufgebaut, damit die bestehenden Tabellen gelöscht und neu angelegt werden.
		 */
		JPAUtil.createEntityManagerForDelition().close();

	}

	private static void initApplication() throws Exception {

		Benutzer ersterAdmin = null;
		Benutzer ersterFilialeLagerMitarbeiter = null;
		Benutzer ersterKasseMitarbeiter = null;
		Lager lager = null;
		Moebelhaus moebelhaus = null;

		String strPlz = "";

		try {
			Properties props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("moebelhaus.properties"));

			/* erster Administrator */
			String benutzerName = props.getProperty("erster_admin_name");
			String benutzerVorname = props.getProperty("erster_admin_vorname");
			String benutzerStrasse = props.getProperty("erster_admin_adresse_strasse");
			strPlz = props.getProperty("erster_admin_adresse_plz");
			String benutzerOrt = props.getProperty("erster_admin_adresse_ort");
			String benutzerEmail = props.getProperty("erster_admin_kontakt_mail");
			String benutzerTelefon = props.getProperty("erster_admin_kontakt_telefon");
			String benutzerBenutzername = props.getProperty("erster_admin_credentials_benutzername");
			String benutzerKennwort = props.getProperty("erster_admin_credentials_kennwort");

			int benutzerPlz = Integer.parseInt(strPlz);

			ersterAdmin = new Benutzer(benutzerName, benutzerVorname,
					new Adresse(benutzerStrasse, benutzerPlz, benutzerOrt), new Kontakt(benutzerEmail, benutzerTelefon),
					new Credentials(benutzerBenutzername, benutzerKennwort), RolleTyp.ADMINISTRATOR);

			/* erster FilialeLager-Mitarbeiter */
			benutzerName = props.getProperty("erster_moebelhaus_lager_name");
			benutzerVorname = props.getProperty("erster_moebelhaus_lager_vorname");
			benutzerStrasse = props.getProperty("erster_moebelhaus_lager_adresse_strasse");
			strPlz = props.getProperty("erster_moebelhaus_lager_adresse_plz");
			benutzerOrt = props.getProperty("erster_moebelhaus_lager_adresse_ort");
			benutzerEmail = props.getProperty("erster_moebelhaus_lager_kontakt_mail");
			benutzerTelefon = props.getProperty("erster_moebelhaus_lager_kontakt_telefon");
			benutzerBenutzername = props.getProperty("erster_moebelhaus_lager_credentials_benutzername");
			benutzerKennwort = props.getProperty("erster_moebelhaus_lager_credentials_kennwort");

			benutzerPlz = Integer.parseInt(strPlz);

			ersterFilialeLagerMitarbeiter = new Benutzer(benutzerName, benutzerVorname,
					new Adresse(benutzerStrasse, benutzerPlz, benutzerOrt), new Kontakt(benutzerEmail, benutzerTelefon),
					new Credentials(benutzerBenutzername, benutzerKennwort), RolleTyp.FILIALE_SACHBEARBEITER);

			/* Erster Kasse-Mitarbeiter */
			benutzerName = props.getProperty("erster_kasse_mitarbeiter_name");
			benutzerVorname = props.getProperty("erster_kasse_mitarbeiter_vorname");
			benutzerStrasse = props.getProperty("erster_kasse_mitarbeiter_adresse_strasse");
			strPlz = props.getProperty("erster_kasse_mitarbeiter_adresse_plz");
			benutzerOrt = props.getProperty("erster_kasse_mitarbeiter_adresse_ort");
			benutzerEmail = props.getProperty("erster_kasse_mitarbeiter_kontakt_mail");
			benutzerTelefon = props.getProperty("erster_kasse_mitarbeiter_kontakt_telefon");
			benutzerBenutzername = props.getProperty("erster_kasse_mitarbeiter_credentials_benutzername");
			benutzerKennwort = props.getProperty("erster_kasse_mitarbeiter_credentials_kennwort");

			benutzerPlz = Integer.parseInt(strPlz);

			ersterKasseMitarbeiter = new Benutzer(benutzerName, benutzerVorname,
					new Adresse(benutzerStrasse, benutzerPlz, benutzerOrt), new Kontakt(benutzerEmail, benutzerTelefon),
					new Credentials(benutzerBenutzername, benutzerKennwort), RolleTyp.KASSE_MITARBEITER);

			/* Filiale */
			String lagerName = props.getProperty("lager_name");
			String filialeName = props.getProperty("moebelhaus_name");
			String filialeCode = props.getProperty("moebelhaus_code");
			String filialeStrasse = props.getProperty("moebelhaus_adresse_strasse");
			strPlz = props.getProperty("moebelhaus_adresse_plz");
			String filialeOrt = props.getProperty("moebelhaus_adresse_ort");
			String filialeEmail = props.getProperty("moebelhaus_kontakt_email");
			String filialeTelefon = props.getProperty("moebelhaus_kontakt_telefon");

			int plz = Integer.parseInt(strPlz);

			lager = new Lager(lagerName);

			moebelhaus = new Moebelhaus(filialeCode, filialeName, new Adresse(filialeStrasse, plz, filialeOrt),
					new Kontakt(filialeEmail, filialeTelefon), lager);

			/* Ersten Admin speichern */
			ersterAdmin = new BenutzerDAOImpl().save(ersterAdmin);
			/* Ersten FilialeLager-Mitarbeiter speichern */
			ersterFilialeLagerMitarbeiter = new BenutzerDAOImpl().save(ersterFilialeLagerMitarbeiter);
			/* Ersten Admin speichern */
			ersterKasseMitarbeiter = new BenutzerDAOImpl().save(ersterKasseMitarbeiter);

			/* Lager speichern */
			lager = new LagerDAOImpl().save(lager);

			/* Filiale speichern */
			moebelhaus = new MoebelhausDAOImpl().save(moebelhaus);

		} catch (NumberFormatException e) {
			logger.error("Der Wert für Postleitzahl [" + strPlz + "] ist nicht korrekt: ", e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			logger.error("Fehler beim Einlesen der property-Datei: ", e);
			throw new RuntimeException(e);
		}
	}

	private static void initTestdata() {
		try {
			initRegal();
			initLieferant();
			initProduktTyp();
			initProdukt();
			initBenutzer();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private static void initRegal() throws Exception {

		int anzahl = 20;

		Lager lager = null;
		LagerDAO lagerDao = new LagerDAOImpl();
		RegalDAO regalDao = new RegalDAOImpl();
		TablarDAO tablarDao = new TablarDAOImpl();

		for (int i = 0; i < anzahl; i++) {

			Regal r = new Regal(i + 1);
			lager = lagerDao.findAll().get(0);

			r.setBezeichnung("R" + r.getNummer());

			r.getTablarListe().add(new Tablar(r.getBezeichnung() + "-" + "TA0"));
			r.getTablarListe().add(new Tablar(r.getBezeichnung() + "-" + "TB0"));
			r.getTablarListe().add(new Tablar(r.getBezeichnung() + "-" + "TC0"));
			r.getTablarListe().add(new Tablar(r.getBezeichnung() + "-" + "TD0"));
			r.getTablarListe().add(new Tablar(r.getBezeichnung() + "-" + "TA1"));
			r.getTablarListe().add(new Tablar(r.getBezeichnung() + "-" + "TB1"));
			r.getTablarListe().add(new Tablar(r.getBezeichnung() + "-" + "TC1"));
			r.getTablarListe().add(new Tablar(r.getBezeichnung() + "-" + "TD1"));
			r.getTablarListe().add(new Tablar(r.getBezeichnung() + "-" + "TA2"));
			r.getTablarListe().add(new Tablar(r.getBezeichnung() + "-" + "TB2"));
			r.getTablarListe().add(new Tablar(r.getBezeichnung() + "-" + "TC2"));
			r.getTablarListe().add(new Tablar(r.getBezeichnung() + "-" + "TD2"));

			for (Tablar t : r.getTablarListe()) {
				tablarDao.save(t);
			}

			regalDao.save(r);
			lager.getRegalListe().add(r);
		}

		lagerDao.update(lager);

	}

	private static void initLieferant() throws Exception {

		String xmlDateiName = "/lieferantInit.xml";
		String schemaDateiName = "/lieferantInitSchema.xsd";

		List<Lieferant> lieferantListe = new ArrayList<Lieferant>();

		try {

			/* Laden und Validieren der XML-Datei */

			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

			URL xsdURL = AppInitializer.class.getResource(schemaDateiName).toURI().toURL();
			Schema schema = schemaFactory.newSchema(xsdURL);

			XMLReaderJDOMFactory factory = new XMLReaderSchemaFactory(schema);

			SAXBuilder sb = new SAXBuilder(factory);

			URL xmlURL = AppInitializer.class.getResource(xmlDateiName).toURI().toURL();
			Document doc = sb.build(xmlURL);

			Element lieferanten = doc.getRootElement();

			List<Element> lieferantElementListe = lieferanten.getChildren("lieferant");

			int index = 0;

			logger.info(">> Erzeugung von Möbelhäuser gestartet.");

			for (Element element : lieferantElementListe) {

				element = lieferantElementListe.get(index++);

				int plz = Integer.parseInt(element.getChildText("plz"));

				Lieferant lieferant = new Lieferant(element.getChildText("name"),
						new Adresse(element.getChildText("strasse"), plz, element.getChildText("ort")),
						new Kontakt(element.getChildText("email"), element.getChildText("telefon")));
				lieferantListe.add(lieferant);

				logger.info("    >> Lieferant " + lieferant.getName() + " erzeugt.");

			}

			logger.info(">> Erzeugung von Möbelhäuser beendet.");

			LieferantDAO dao = new LieferantDAOImpl();

			for (Lieferant lieferant : lieferantListe) {
				dao.save(lieferant);
				logger.info(">> Lieferant mit Id-Nr. " + lieferant.getId() + " wurde in die Datebank gespeichert.");
				lieferantenMap.put(lieferant.getName(), lieferant);
			}

		} catch (JDOMException e) {
			logger.error("Fehler bei der Validierung von " + xmlDateiName, e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			logger.error("Fehler beim Lesen der XML-Datei: " + xmlDateiName + ".", e);
			throw new RuntimeException(e);
		}
	}

	private static void initProduktTyp() throws Exception {

		String xmlDateiName = "/produktTypInit.xml";
		String schemaDateiName = "/produktTypInitSchema.xsd";

		List<ProduktTyp> produktTypListe = new ArrayList<ProduktTyp>();

		try {

			/* Laden und Validieren der XML-Datei */

			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

			URL xsdURL = AppInitializer.class.getResource(schemaDateiName).toURI().toURL();
			Schema schema = schemaFactory.newSchema(xsdURL);

			XMLReaderJDOMFactory factory = new XMLReaderSchemaFactory(schema);

			SAXBuilder sb = new SAXBuilder(factory);

			URL xmlURL = AppInitializer.class.getResource(xmlDateiName).toURI().toURL();
			Document doc = sb.build(xmlURL);

			Element hersteller = doc.getRootElement();

			List<Element> produktTypElementliste = hersteller.getChildren("produktTyp");

			int index = 0;

			logger.info(">> Erzeugung von ProduktTypen gestartet.");

			List<Lieferant> lieferantenListe = new LieferantDAOImpl().findAll();

			for (Element element : produktTypElementliste) {

				element = produktTypElementliste.get(index++);

				Double preis = Double.parseDouble(element.getChildText("preis"));
				ProduktTyp pTyp = new ProduktTyp(element.getChildText("name"), element.getChildText("beschreibung"),
						preis, element.getChildText("code"), lieferantenMap.get(element.getChildText("lieferant")));
				pTyp.setMinimalerBestand(Integer.parseInt(element.getChildText("min-bestand")));
				produktTypListe.add(pTyp);

				logger.info("    >> ProduktTyp mit Code " + pTyp.getTypCode() + " erzeugt.");

			}

			logger.info(">> Erzeugung von ProduktTypen beendet.");

			TablarDAO tablarDAO = new TablarDAOImpl();
			ProduktTypDAO produktTypDAO = new ProduktTypDAOImpl();
			index = 0;

			for (ProduktTyp pTyp : produktTypListe) {
				pTyp.setMaximalerBestand(50);
				pTyp.setAblageTablar(tablarDAO.findAll().get(index++));
				produktTypDAO.save(pTyp);
				logger.info(">> ProduktTyp mit Id-Nr. " + pTyp.getId() + " wurde in die Datebank gespeichert.");
			}

		} catch (JDOMException e) {
			logger.error("Fehler bei der Validierung von " + xmlDateiName, e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			logger.error("Fehler beim Lesen der XML-Datei: " + xmlDateiName + ".", e);
			throw new RuntimeException(e);
		}

	}

	private static void initProdukt() throws Exception {

		List<ProduktTyp> produktTypListe = new ProduktTypDAOImpl().findAll();

		ProduktDAO dao = new ProduktDAOImpl();

		logger.info(">> Erzeugung von Produkte gestartet.");

		for (ProduktTyp pTyp : produktTypListe) {

			int anzahlProdukte = ThreadLocalRandom.current().nextInt(pTyp.getMinimalerBestand(),
					pTyp.getMaximalerBestand());

			for (int i = 1; i <= anzahlProdukte; i++) {
				Produkt p = new Produkt(pTyp, getProduktCode());
				dao.save(p);
				logger.info("        >> Produkt vom Typ " + pTyp.getTypCode() + " mit Id-Nr. " + p.getId()
						+ " wurde in die Datebank gespeichert.");
			}
			logger.info("   >> " + anzahlProdukte + " Produkt(e) von Typ " + pTyp.getTypCode() + " erzeugt.");
		}

		logger.info(">> Erzeugung von Produkte beendet.");
	}

	private static void initBenutzer() throws Exception {

		String xmlDateiName = "/benutzerInit.xml";
		String schemaDateiName = "/benutzerInitSchema.xsd";

		List<Benutzer> benutzerListe = new ArrayList<Benutzer>();

		try {

			/* Laden und Validieren der XML-Datei */

			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

			URL xsdURL = AppInitializer.class.getResource(schemaDateiName).toURI().toURL();
			Schema schema = schemaFactory.newSchema(xsdURL);

			XMLReaderJDOMFactory factory = new XMLReaderSchemaFactory(schema);

			SAXBuilder sb = new SAXBuilder(factory);

			URL xmlURL = AppInitializer.class.getResource(xmlDateiName).toURI().toURL();
			Document doc = sb.build(xmlURL);

			Element users = doc.getRootElement();

			List<Element> benutzerElementliste = users.getChildren("benutzer");

			int index = 0;

			logger.info(">> Erzeugung von Benutzer gestartet.");

			for (Element element : benutzerElementliste) {

				element = benutzerElementliste.get(index++);

				int plz = Integer.parseInt(element.getChildText("plz"));

				RolleTyp rolleTyp = null;

				if (element.getChildText("rolle").equals("Administrator")) {
					rolleTyp = RolleTyp.ADMINISTRATOR;
				} else if (element.getChildText("rolle").equals("Kasse-Mitarbeiter")) {
					rolleTyp = RolleTyp.KASSE_MITARBEITER;
				} else if (element.getChildText("rolle").equals("Filiale-Sachbearbeiter")) {
					rolleTyp = RolleTyp.FILIALE_SACHBEARBEITER;
				} else if (element.getChildText("rolle").equals("Filiale-Leiter")) {
					rolleTyp = RolleTyp.FILIALE_LEITER;
				} else {
					logger.error("Fehler: RolleTyp nicht gefunden");
					throw new RuntimeException();
				}

				Benutzer benutzer = new Benutzer(element.getChildText("nachname"), element.getChildText("vorname"),
						new Adresse(element.getChildText("strasse"), plz, element.getChildText("ort")),
						new Kontakt(element.getChildText("email"), element.getChildText("telefon")),
						new Credentials(element.getChildText("benutzername"), element.getChildText("passwort")),
						rolleTyp);
				benutzerListe.add(benutzer);

				logger.info("    >> Benutzer " + benutzer.getVorname() + " " + benutzer.getNachname() + " erzeugt.");

			}

			logger.info(">> Erzeugung von Benutzer beendet.");

			BenutzerDAO dao = new BenutzerDAOImpl();

			for (Benutzer b : benutzerListe) {
				dao.save(b);
				logger.info(">> Benutzer mit Id-Nr. " + b.getId() + " wurde in die Datebank gespeichert.");
			}

		} catch (JDOMException e) {
			logger.error("Fehler bei der Validierung von " + xmlDateiName, e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			logger.error("Fehler beim Lesen der XML-Datei: " + xmlDateiName + ".", e);
			throw new RuntimeException(e);
		}
	}

	/* Helper-Methode */
	private static long getProduktCode() {

		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		return new GregorianCalendar().getTimeInMillis();
	}
}
