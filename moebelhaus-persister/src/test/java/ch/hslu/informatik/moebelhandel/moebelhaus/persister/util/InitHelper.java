package ch.hslu.informatik.moebelhandel.moebelhaus.persister.util;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Adresse;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Bestellung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.BestellungPosition;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Credentials;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Kontakt;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lager;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lieferant;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lieferung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.LieferungPosition;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Moebelhaus;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Person;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Produkt;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktRuecknahme;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Rechnung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.RechnungPosition;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Regal;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.RolleTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Tablar;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.BenutzerDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.BestellungDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.CredentialsDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.LagerDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.LieferantDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.LieferungDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.LieferungPositionDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.MoebelhausDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.PersonDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.ProduktDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.ProduktRuecknahmeDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.ProduktTypDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.RechnungDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.RegalDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.TablarDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.BenutzerDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.BestellungDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.CredentialsDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.LagerDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.LieferantDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.LieferungDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.LieferungPositionDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.MoebelhausDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.PersonDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.ProduktDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.ProduktRuecknahmeDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.ProduktTypDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.RechnungDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.RegalDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.TablarDAOImpl;

public class InitHelper {

	public static final int INIT_SIZE_LIEFERANT = 3;
	public static final int INIT_SIZE_PRODUKT_TYP = 9;
	public static final int INIT_SIZE_PRODUKT = 9;
	public static final int INIT_SIZE_PERSON = 6;
	public static final int INIT_SIZE_LAGER = 3;
	public static final int INIT_SIZE_MOEBELHAUS = 3;
	public static final int INIT_SIZE_CREDENTIALS = 6;
	public static final int INIT_SIZE_BENUTZER = 4;
	public static final int INIT_SIZE_BESTELLUNG_POSITION = 3;
	public static final int INIT_SIZE_BESTELLUNG = 1;
	public static final int INIT_SIZE_LIEFERUNG_POSITION = 6;
	public static final int INIT_SIZE_LIEFERUNG = 2;
	public static final int INIT_SIZE_RECHNUNG = 2;
	public static final int INIT_SIZE_PRODUKT_RUECKNAHME = 2;

	/* Regal */
	public static List<Regal> initRegal(int anzahl) throws Exception {

		RegalDAO pRegal = new RegalDAOImpl();
		TablarDAO pTablar = new TablarDAOImpl();

		List<Regal> regalListe = new ArrayList<Regal>();

		for (int i = 0; i < anzahl; i++) {
			Regal r = new Regal((i + 1));
			r.setBezeichnung("R" + r.getNummer());

			r.getTablarListe().add(new Tablar("TA0"));
			r.getTablarListe().add(new Tablar("TB0"));
			r.getTablarListe().add(new Tablar("TC0"));
			r.getTablarListe().add(new Tablar("TD0"));
			r.getTablarListe().add(new Tablar("TA1"));
			r.getTablarListe().add(new Tablar("TB1"));
			r.getTablarListe().add(new Tablar("TC1"));
			r.getTablarListe().add(new Tablar("TD1"));
			r.getTablarListe().add(new Tablar("TA2"));
			r.getTablarListe().add(new Tablar("TB2"));
			r.getTablarListe().add(new Tablar("TC2"));
			r.getTablarListe().add(new Tablar("TD2"));

			for (Tablar t : r.getTablarListe()) {
				pTablar.save(t);
			}

			pRegal.save(r);

			regalListe.add(r);
		}

		return regalListe;

	}

	public static void deleteAllRegal() throws Exception {
		RegalDAO pRegal = new RegalDAOImpl();
		for (Regal e : pRegal.findAll()) {
			pRegal.delete(e);
		}
	}

	/* Lieferant */
	public static List<Lieferant> initLieferant() throws Exception {

		/* Es werden 3 Lieferanten angelegt (INIT_SIZE_LIEFERANT) */
		LieferantDAO pLieferant = new LieferantDAOImpl();

		List<Lieferant> liferantListe = new ArrayList<Lieferant>();

		liferantListe.add(new Lieferant("Möbel Weber GmbH", new Adresse("Gothardstrasse 60", 6484, "Wassen"),
				new Kontakt("info@moebel.weber.ch", "079 876 76 65")));
		liferantListe.add(new Lieferant("Holzmöbel Fischer AG", new Adresse("Bergstrasse 28", 6440, "Brunnen"),
				new Kontakt("info@holzmoebel.fischer.ch", "076 765 65 54")));
		liferantListe.add(new Lieferant("Möbel Egger AG", new Adresse("Luzernerstrasse 33", 6274, "Eschenbach"),
				new Kontakt("info@moebel.oegger.ch", "078 654 54 43")));

		for (Lieferant l : liferantListe) {
			pLieferant.save(l);
		}

		return liferantListe;
	}

	public static void deleteAllLieferant() throws Exception {
		LieferantDAO pLieferant = new LieferantDAOImpl();
		for (Lieferant e : pLieferant.findAll()) {
			pLieferant.delete(e);
		}
	}

	/* ProduktTyp */
	public static List<ProduktTyp> initProduktTyp() throws Exception {

		ProduktTypDAO pProduktTyp = new ProduktTypDAOImpl();

		List<Lieferant> lieferantListe = new LieferantDAOImpl().findAll();

		List<ProduktTyp> liste = new ArrayList<ProduktTyp>();

		/* Lieferant 1 */
		liste.add(new ProduktTyp("Computer-Tisch",
				"Lackierte Korpusteile in Mattlack, weiss, 3 Schubkästen mit Superweissglasblenden, B 132 T 60 H 75 cm",
				699.00, "CmpT-KSTF-CSL-440", lieferantListe.get(0)));

		liste.add(new ProduktTyp("Computer-Tisch",
				"Klarglas, Seitenteile Aluminium geschliffen, Kabelrückwand, parkettgeeignete Rollen, Belastbarkeit 40 kg, B 80 T 53 H 76 cm",
				359.00, "CmpT-KSTF-CU-MR-200-E", lieferantListe.get(0)));

		liste.add(new ProduktTyp("Schreibtisch",
				"Sheeshamholz lackiert, Schubladenfronten Kuhfell, Gestell Eisen, geschnitzte Fronten, B 116 T 60 H 78 cm",
				1099.00, "ShrT-KSTF-CU-BT-140-ER", lieferantListe.get(0)));

		/* Lieferant 2 */
		liste.add(new ProduktTyp("Gartentisch",
				"Gartentisch Oxford aus Holz, Wetterbeständig, Teakholz massiv 200 x 100 cm", 699.00, "GT-HLZ-RUM-08",
				lieferantListe.get(1)));

		liste.add(new ProduktTyp("Gartentisch",
				"Gartentisch Oskar aus Holz, Wetterbeständig, Teakholz massiv 180 x 90 cm", 799.00, "GT-HLZ-AXF-26",
				lieferantListe.get(1)));

		liste.add(new ProduktTyp("Gartenstuhl",
				"Gartenstuhl Larino aus Holz, Wetterbeständig, passend zu Tisch GT-HLZ-AXF-26, B 62 T 68 H 110 cm",
				155.00, "GSTH-HLZ-RUM-08-STH-01", lieferantListe.get(1)));

		/* Lieferant 3 */
		liste.add(new ProduktTyp("Sofa",
				"2.5er-Sofa Adela, Leder Columbia classicgrau, B 170 T 83 H 84 cm, ST 54 SB 140 SH 44 cm", 2375.00,
				"SOFA-LD-CLMB-72-1", lieferantListe.get(2)));

		liste.add(new ProduktTyp("Sofa",
				"2.5er-Sofa Classics 650, Bezug Leder siena, ohne Ledertrennungsnaht, Sitz-/Rückenkissen mit Dickfadenziernaht, Metallfuss hochglanz höhenverstellbar, B 188 T 87 H 84 cm - Sitztiefe 53 cm",
				2375.00, "SOFA-LD-IT-23-4", lieferantListe.get(2)));

		liste.add(new ProduktTyp("Sessel", "Bezug: Vintage Lederlook, B 72 T 82 H 102 cm", 490.00, "SS-CHN-UT-14-6",
				lieferantListe.get(2)));

		for (ProduktTyp t : liste) {
			pProduktTyp.save(t);
		}

		return liste;

	}

	public static void deleteAllProduktTyp() throws Exception {
		ProduktTypDAO pProduktTyp = new ProduktTypDAOImpl();
		for (ProduktTyp e : pProduktTyp.findAll()) {
			pProduktTyp.delete(e);
		}
	}

	/* Produkt */
	public static List<Produkt> initProdukt() throws Exception {

		ProduktDAO pProdukt = new ProduktDAOImpl();
		ProduktTypDAO pProduktTyp = new ProduktTypDAOImpl();

		List<Produkt> liste = new ArrayList<Produkt>();

		ProduktTyp gartenTisch = pProduktTyp.findByTypCode("GT-HLZ-RUM-08");
		ProduktTyp sofa = pProduktTyp.findByTypCode("SOFA-LD-IT-23-4");
		ProduktTyp sessel = pProduktTyp.findByTypCode("SS-CHN-UT-14-6");

		/* Drei Gartentische erstellen */
		liste.add(new Produkt(gartenTisch, 111111));
		liste.add(new Produkt(gartenTisch, 111112));
		liste.add(new Produkt(gartenTisch, 111113));

		/* Drei Sofas erstellen */
		liste.add(new Produkt(sofa, 111121));
		liste.add(new Produkt(sofa, 111122));
		liste.add(new Produkt(sofa, 111123));

		/* Drei Sessel erstellen */
		liste.add(new Produkt(sessel, 111131));
		liste.add(new Produkt(sessel, 111132));
		liste.add(new Produkt(sessel, 111133));

		for (Produkt p : liste) {
			pProdukt.save(p);
		}

		return liste;
	}

	public static void deleteAllProdukt() throws Exception {
		ProduktDAO pProdukt = new ProduktDAOImpl();
		for (Produkt p : pProdukt.findAll()) {
			pProdukt.delete(p);
		}
	}

	/* Person */
	public static List<Person> initPerson() throws Exception {

		PersonDAO pPerson = new PersonDAOImpl();
		List<Person> liste = new ArrayList<Person>();

		liste.add(new Person("Weber", "Marco", new Adresse("Lindenstrasse 12", 6030, "Ebikon"),
				new Kontakt("mweber@gmail.com", "041 111 11 11")));
		liste.add(new Person("Fischer", "Ana", new Adresse("Bundesstrasse 112", 6002, "Luzern"),
				new Kontakt("afischer1980@sbb.ch", "041 222 22 22")));
		liste.add(new Person("Pechvogel", "Hansli", new Adresse("Kirchgasse 4", 6274, "Eschenbach"),
				new Kontakt("hpechvogel@bluewin.ch", "041 333 33 33")));
		liste.add(new Person("Kaufmann", "Ursula", new Adresse("Rigistrasse 26", 6048, "Horw"),
				new Kontakt("ukaufmann@yahoo.com", "041 444 44 44")));
		liste.add(new Person("Portmann", "Roger", new Adresse("Unterdorfstrasse 12", 6033, "Buchrain"),
				new Kontakt("rportmann1975@sunrise.ch", "041 555 55 55")));
		liste.add(new Person("Lindauer", "Adrian", new Adresse("Gothardstrasse 64", 6484, "Wassen"),
				new Kontakt("alindauer@bluewin.ch", "041 666 66 66")));

		for (Person p : liste) {
			pPerson.save(p);
		}

		return liste;
	}

	public static void deleteAllPerson() throws Exception {
		PersonDAO pPerson = new PersonDAOImpl();
		for (Person p : pPerson.findAll()) {
			pPerson.delete(p);
		}
	}

	/* Lager */
	public static List<Lager> initLager(int i) throws Exception {
		/* Regale holen */
		List<Regal> regalListe = new RegalDAOImpl().findAll();

		LagerDAO pLager = new LagerDAOImpl();
		List<Lager> liste = new ArrayList<Lager>();

		Lager lagerA = new Lager("Lager A");
		Lager lagerB = new Lager("Lager B");
		Lager lagerC = new Lager("Lager C");

		/* Lager mit Regale versehen */
		lagerA.addRegal(regalListe.get(0));
		lagerA.addRegal(regalListe.get(1));
		lagerA.addRegal(regalListe.get(2));
		lagerA.addRegal(regalListe.get(3));

		lagerB.addRegal(regalListe.get(4));
		lagerB.addRegal(regalListe.get(5));
		lagerB.addRegal(regalListe.get(6));
		lagerB.addRegal(regalListe.get(7));
		lagerB.addRegal(regalListe.get(8));
		lagerB.addRegal(regalListe.get(9));

		lagerC.addRegal(regalListe.get(10));
		lagerC.addRegal(regalListe.get(11));
		lagerC.addRegal(regalListe.get(12));
		lagerC.addRegal(regalListe.get(13));
		lagerC.addRegal(regalListe.get(14));
		lagerC.addRegal(regalListe.get(15));
		lagerC.addRegal(regalListe.get(16));
		lagerC.addRegal(regalListe.get(17));

		liste.add(lagerA);
		liste.add(lagerB);
		liste.add(lagerC);

		for (Lager l : liste) {
			pLager.save(l);
		}

		return liste;
	}

	public static void deleteAllLager() throws Exception {

		LagerDAO pLager = new LagerDAOImpl();

		for (Lager l : pLager.findAll()) {
			pLager.delete(l);
		}
	}

	/* Filiale */
	public static void initMoebelhaus(int i) throws Exception {

		List<Lager> lagerListe = new LagerDAOImpl().findAll();
		List<Moebelhaus> liste = new ArrayList<Moebelhaus>();

		MoebelhausDAO pMoebelhaus = new MoebelhausDAOImpl();

		liste.add(new Moebelhaus("FILIALE_LU_01", "Filiale Luzern 01", new Adresse("Bundesstrasse 80", 6002, "Luzern"),
				new Kontakt("mail@flu01.bluewin.ch", "041 422 33 44"), lagerListe.get(0)));

		liste.add(new Moebelhaus("FILIALE_LU_02", "Filiale Luzern 02",
				new Adresse("Luzernerstrasse 40", 6274, "Eschenbach"),
				new Kontakt("mail@flu02.bluewin.ch", "041 644 22 22"), lagerListe.get(1)));

		liste.add(new Moebelhaus("FILIALE_UR_01", "Filiale Uri 01", new Adresse("Gothardstrasse 62", 6460, "Altdorf"),
				new Kontakt("mail@fur01.bluewin.ch", "041 622 84 84"), lagerListe.get(2)));

		for (Moebelhaus f : liste) {
			pMoebelhaus.save(f);
		}

	}

	public static void deleteAllMoebelhaus() throws Exception {

		MoebelhausDAO pMoebelhaus = new MoebelhausDAOImpl();
		for (Moebelhaus f : pMoebelhaus.findAll()) {
			pMoebelhaus.delete(f);
		}
	}

	/* Credentials */
	public static List<Credentials> initCredentials() throws Exception {

		CredentialsDAO pCredentials = new CredentialsDAOImpl();
		List<Credentials> liste = new ArrayList<Credentials>();

		liste.add(new Credentials("hpechvogel", "hpechvogel_pwd"));
		liste.add(new Credentials("mweber", "mweber_pwd"));
		liste.add(new Credentials("rbucher", "rbucher_pwd"));
		liste.add(new Credentials("ukaufmann", "ukaufmann_pwd"));
		liste.add(new Credentials("lkronenberg", "lkronenberg_pwd"));
		liste.add(new Credentials("alindauer", "alindauer_pwd"));

		for (Credentials c : liste) {
			pCredentials.save(c);
		}

		return liste;
	}

	public static void deleteAllCredentials() throws Exception {

		CredentialsDAO pCredentials = new CredentialsDAOImpl();
		for (Credentials c : pCredentials.findAll()) {
			pCredentials.delete(c);
		}
	}

	/* Person */
	public static List<Benutzer> initBenutzer() throws Exception {

		BenutzerDAO pBenutzer = new BenutzerDAOImpl();

		List<Benutzer> liste = new ArrayList<Benutzer>();

		liste.add(new Benutzer("Weber", "Marco", new Adresse("Lindenstrasse 12", 6030, "Ebikon"),
				new Kontakt("mweber@gmail.com", "041 111 11 11"), new Credentials("mweber", "mweber_pwd"),
				RolleTyp.FILIALE_SACHBEARBEITER));
		liste.add(new Benutzer("Fischer", "Ana", new Adresse("Bundesstrasse 112", 6002, "Luzern"),
				new Kontakt("afischer1980@sbb.ch", "041 222 22 22"), new Credentials("afischer", "afischer_pwd"),
				RolleTyp.FILIALE_SACHBEARBEITER));
		liste.add(new Benutzer("Portmann", "Roger", new Adresse("Unterdorfstrasse 12", 6033, "Buchrain"),
				new Kontakt("rportmann1975@sunrise.ch", "041 555 55 55"), new Credentials("rportmann", "rportmann_pwd"),
				RolleTyp.KASSE_MITARBEITER));
		liste.add(new Benutzer("Lindauer", "Adrian", new Adresse("Gothardstrasse 64", 6484, "Wassen"),
				new Kontakt("alindauer@bluewin.ch", "041 666 66 66"), new Credentials("alindauer", "alindauer_pwd"),
				RolleTyp.KASSE_MITARBEITER));

		for (Benutzer b : liste) {
			pBenutzer.save(b);
		}

		return liste;
	}

	public static void deleteAllBenutzer() throws Exception {

		BenutzerDAO pBenutzer = new BenutzerDAOImpl();

		for (Benutzer b : pBenutzer.findAll()) {
			pBenutzer.delete(b);
		}

	}

	/* BestellungFiliale */
	public static List<Bestellung> initBestellung() throws Exception {

		BestellungDAO pBestellungDAO = new BestellungDAOImpl();
		MoebelhausDAO pMoebelhaus = new MoebelhausDAOImpl();

		List<Bestellung> liste = new ArrayList<Bestellung>();

		Moebelhaus moebelhausLu01 = pMoebelhaus.findByMoebelhausCode("FILIALE_LU_01");

		Bestellung b = new Bestellung(new GregorianCalendar(), moebelhausLu01);

		/* BestellungPositionen der Bestellung hinzufügen */
		ProduktTyp gartenTisch = new ProduktTypDAOImpl().findByTypCode("GT-HLZ-RUM-08");
		ProduktTyp sofa = new ProduktTypDAOImpl().findByTypCode("SOFA-LD-CLMB-72-1");
		ProduktTyp sessel = new ProduktTypDAOImpl().findByTypCode("SS-CHN-UT-14-6");

		b.getBestellungPositionListe().add(new BestellungPosition(gartenTisch, 3));
		b.getBestellungPositionListe().add(new BestellungPosition(sofa, 1));
		b.getBestellungPositionListe().add(new BestellungPosition(sessel, 4));

		liste.add(b);

		for (Bestellung bestellung : liste) {
			pBestellungDAO.save(bestellung);
		}

		return liste;
	}

	public static void deleteAllBestellung() throws Exception {

		BestellungDAO pBestellungDAO = new BestellungDAOImpl();

		for (Bestellung b : pBestellungDAO.findAll()) {
			pBestellungDAO.delete(b);
		}

	}

	/* LieferungPosition */
	public static List<LieferungPosition> initLieferungPosition() throws Exception {

		LieferungPositionDAO pLieferungPosition = new LieferungPositionDAOImpl();

		List<LieferungPosition> liste = new ArrayList<LieferungPosition>();

		List<ProduktTyp> produktTypListe = new ProduktTypDAOImpl().findAll();

		liste.add(new LieferungPosition(produktTypListe.get(0), 3));
		liste.add(new LieferungPosition(produktTypListe.get(1), 2));
		liste.add(new LieferungPosition(produktTypListe.get(2), 3));
		liste.add(new LieferungPosition(produktTypListe.get(3), 2));
		liste.add(new LieferungPosition(produktTypListe.get(4), 2));
		liste.add(new LieferungPosition(produktTypListe.get(5), 3));

		for (LieferungPosition p : liste) {
			pLieferungPosition.save(p);
		}

		return liste;
	}

	public static void deleteAllLieferungPosition() throws Exception {

		LieferungPositionDAO pLieferungPosition = new LieferungPositionDAOImpl();

		for (LieferungPosition p : pLieferungPosition.findAll()) {
			pLieferungPosition.delete(p);
		}
	}

	/* Lieferung */
	public static List<Lieferung> initLieferung() throws Exception {

		LieferungDAO pLieferung = new LieferungDAOImpl();

		List<Lieferung> liste = new ArrayList<Lieferung>();

		List<Moebelhaus> filialeListe = new MoebelhausDAOImpl().findAll();
		List<LieferungPosition> lieferungPositionListe = new LieferungPositionDAOImpl().findAll();

		Lieferung lieferungA = new Lieferung(new GregorianCalendar(2016, 3, 22), filialeListe.get(0));
		Lieferung lieferungB = new Lieferung(new GregorianCalendar(2016, 3, 25), filialeListe.get(1));

		liste.add(lieferungA);
		liste.add(lieferungB);

		lieferungA.getLieferungPositionListe().add(lieferungPositionListe.get(0));
		lieferungA.getLieferungPositionListe().add(lieferungPositionListe.get(1));
		lieferungA.getLieferungPositionListe().add(lieferungPositionListe.get(2));

		lieferungB.getLieferungPositionListe().add(lieferungPositionListe.get(3));
		lieferungB.getLieferungPositionListe().add(lieferungPositionListe.get(4));
		lieferungB.getLieferungPositionListe().add(lieferungPositionListe.get(5));

		for (Lieferung l : liste) {
			pLieferung.save(l);
		}

		return liste;

	}

	public static void deleteAllLieferung() throws Exception {

		LieferungDAO pLieferung = new LieferungDAOImpl();

		for (Lieferung l : pLieferung.findAll()) {
			pLieferung.delete(l);
		}
	}

	/* Rechnung */
	public static List<Rechnung> initRechnung() throws Exception {

		RechnungDAO pRechnung = new RechnungDAOImpl();

		List<Rechnung> liste = new ArrayList<Rechnung>();

		List<Benutzer> benutzerListe = new BenutzerDAOImpl().findAll();
		List<Produkt> produktListe = new ProduktDAOImpl().findAll();

		Rechnung rechnungA = new Rechnung(new GregorianCalendar(2016, 3, 22), benutzerListe.get(0));
		Rechnung rechnungB = new Rechnung(new GregorianCalendar(2016, 3, 25), benutzerListe.get(0));

		rechnungA.addRechnungPosition(new RechnungPosition(produktListe.get(0).getTyp(), 1));
		rechnungA.addRechnungPosition(new RechnungPosition(produktListe.get(3).getTyp(), 1));
		rechnungA.addRechnungPosition(new RechnungPosition(produktListe.get(6).getTyp(), 1));

		rechnungB.addRechnungPosition(new RechnungPosition(produktListe.get(1).getTyp(), 1));
		rechnungB.addRechnungPosition(new RechnungPosition(produktListe.get(4).getTyp(), 1));

		liste.add(rechnungA);
		liste.add(rechnungB);

		for (Rechnung r : liste) {
			pRechnung.save(r);
		}

		return liste;
	}

	public static void deleteAllRechnung() throws Exception {

		RechnungDAO pRechnung = new RechnungDAOImpl();

		for (Rechnung r : pRechnung.findAll()) {
			pRechnung.delete(r);
		}

	}

	/* ProduktRuecknahme */
	public static List<ProduktRuecknahme> initProduktRuecknahme() throws Exception {

		ProduktRuecknahmeDAO pProduktRuecknahme = new ProduktRuecknahmeDAOImpl();

		List<ProduktRuecknahme> liste = new ArrayList<ProduktRuecknahme>();

		List<ProduktTyp> produktTypListe = new ProduktTypDAOImpl().findAll();

		List<Rechnung> rechnungListe = new RechnungDAOImpl().findAll();

		liste.add(
				new ProduktRuecknahme(rechnungListe.get(0), produktTypListe.get(0), new GregorianCalendar(2016, 3, 22),
						1, "Das Produkt wurde bei der Lieferung, die durch unseren Dienst erfolgte, beschädigt."));
		liste.add(
				new ProduktRuecknahme(rechnungListe.get(0), produktTypListe.get(2), new GregorianCalendar(2016, 3, 22),
						1, "Der Kunde hat sich offenbar anders überlegt und braucht das Produkt doch nicht."));

		for (ProduktRuecknahme p : liste) {
			pProduktRuecknahme.save(p);
		}

		return liste;
	}

	public static void deleteAllProduktRuecknahme() throws Exception {

		ProduktRuecknahmeDAO pProduktRuecknahme = new ProduktRuecknahmeDAOImpl();

		for (ProduktRuecknahme p : pProduktRuecknahme.findAll()) {
			pProduktRuecknahme.delete(p);
		}

	}

	public static void resetDb() {
		/* Schema wird angelegt, die vorhandenen Daten werden dabei gelöscht. */
		JPAUtil.createEntityManagerForDelition().close();
	}
}
