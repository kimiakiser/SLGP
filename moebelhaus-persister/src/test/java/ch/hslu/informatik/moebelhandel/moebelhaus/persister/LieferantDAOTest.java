package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Adresse;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Kontakt;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lieferant;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.LieferantDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.InitHelper;

public class LieferantDAOTest {

	private static LieferantDAO pLieferant = new LieferantDAOImpl();
	private static List<Lieferant> liferantListe = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InitHelper.resetDb();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		InitHelper.deleteAllLieferant();
	}

	@Before
	public void setUp() throws Exception {
		InitHelper.deleteAllLieferant();
	}

	@After
	public void tearDown() throws Exception {
	}

	private void init() throws Exception {
		liferantListe = InitHelper.initLieferant();
	}

	@Test
	public final void testSave() throws Exception {

		liferantListe = new ArrayList<Lieferant>();

		liferantListe.add(new Lieferant("Möbel Weber GmbH", new Adresse("Gothardstrasse 60", 6484, "Wassen"),
				new Kontakt("info@moebel.weber.ch", "079 876 76 65")));
		liferantListe.add(new Lieferant("Holzmöbel Fischer AG", new Adresse("Bergstrasse 28", 6440, "Brunnen"),
				new Kontakt("info@holzmoebel.fischer.ch", "076 765 65 54")));
		liferantListe.add(new Lieferant("Möbel Egger AG", new Adresse("Luzernerstrasse 33", 6274, "Eschenbach"),
				new Kontakt("info@moebel.oegger.ch", "078 654 54 43")));

		for (Lieferant l : liferantListe) {
			pLieferant.save(l);
		}

		assertTrue(liferantListe.size() == 3);
	}

	@Test
	public final void testUpdate() throws Exception {

		init();
		assertTrue(liferantListe.size() == 3);

		/* Kontakt bei Möbel Weber GmbH ändert sich */
		String name = "Möbel Weber GmbH";
		Lieferant lieferantMoebelWeber = pLieferant.findByName(name);
		assertNotNull(lieferantMoebelWeber);

		Kontakt k = lieferantMoebelWeber.getKontakt();

		String emailNeu = "kundendienst@moebel.weber.ch";
		k.setEmail(emailNeu);

		String telefonNeu = "076 888 77 66";
		k.setTelefon(telefonNeu);

		/* Updaten */
		pLieferant.update(lieferantMoebelWeber);

		/* Holen aus der Db */
		Lieferant lieferantFromDb = pLieferant.findByName(name);
		Kontakt kontakt = lieferantFromDb.getKontakt();
		assertTrue(emailNeu.equals(kontakt.getEmail()));
		assertTrue(telefonNeu.equals(kontakt.getTelefon()));

	}

	@Test
	public final void testDelete() throws Exception {

		init();

		int size = liferantListe.size();

		/* Den letzten Liefeanten entferenen */
		Lieferant lastLieferant = liferantListe.get(size - 1);

		pLieferant.delete(lastLieferant);

		/* Alle lieferanten aus der DB holen */
		List<Lieferant> lieferantenFromDb = pLieferant.findAll();
		assertTrue(lieferantenFromDb.size() == size - 1);
	}

	@Test
	public final void testDeleteById() throws Exception {

		init();

		int size = liferantListe.size();

		/* Den letzten Liefeanten entferenen */
		Lieferant lastLieferant = liferantListe.get(size - 1);

		pLieferant.deleteById(lastLieferant.getId());

		/* Alle lieferanten aus der DB holen */
		List<Lieferant> lieferantenFromDb = pLieferant.findAll();
		assertTrue(lieferantenFromDb.size() == size - 1);
	}

	@Test
	public final void testFindByName() throws Exception {

		init();

		/* Namen holen */
		List<String> namen = new ArrayList<String>();

		for (Lieferant l : liferantListe) {
			namen.add(l.getName());
		}

		/* Alle lieferanten aus der DB nach Namen holen */
		for (String lieferantName : namen) {
			Lieferant l = pLieferant.findByName(lieferantName);
			assertNotNull(l);
		}

	}

	@Test
	public final void testFindById() throws Exception {

		init();

		/* Namen holen */
		List<Long> ids = new ArrayList<Long>();

		for (Lieferant l : liferantListe) {
			ids.add(l.getId());
		}

		/* Alle lieferanten aus der DB nach Id holen */
		for (long lieferantId : ids) {
			Lieferant l = pLieferant.findById(lieferantId);
			assertNotNull(l);
		}
	}

	@Test
	public final void testFindAll() throws Exception {

		init();
		assertTrue(pLieferant.findAll().size() == InitHelper.INIT_SIZE_LIEFERANT);
	}

}
