package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktRuecknahme;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.ProduktRuecknahmeDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.InitHelper;

public class ProduktRuecknahmeDAOTest {

	private static ProduktRuecknahmeDAO pProduktRuecknahme = new ProduktRuecknahmeDAOImpl();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InitHelper.resetDb();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		InitHelper.deleteAllProduktRuecknahme();
		InitHelper.deleteAllRechnung();
		InitHelper.deleteAllBenutzer();
		InitHelper.deleteAllProdukt();
		InitHelper.deleteAllProduktTyp();
		InitHelper.deleteAllLieferant();
	}

	@Before
	public void setUp() throws Exception {
		InitHelper.deleteAllProduktRuecknahme();
		InitHelper.deleteAllRechnung();
		InitHelper.deleteAllBenutzer();
		InitHelper.deleteAllProdukt();
		InitHelper.deleteAllProduktTyp();
		InitHelper.deleteAllLieferant();
	}

	@After
	public void tearDown() throws Exception {
	}

	private void init() throws Exception {
		InitHelper.initLieferant();
		InitHelper.initProduktTyp();
		InitHelper.initProdukt();
		InitHelper.initBenutzer();
		InitHelper.initRechnung();
		InitHelper.initProduktRuecknahme();
	}

	@Test
	public final void testSave() throws Exception {
		init();
		assertTrue(pProduktRuecknahme.findAll().size() == InitHelper.INIT_SIZE_PRODUKT_RUECKNAHME);
	}

	@Test
	public final void testUpdate() throws Exception {

		init();
		assertTrue(pProduktRuecknahme.findAll().size() == InitHelper.INIT_SIZE_PRODUKT_RUECKNAHME);

		ProduktRuecknahme erste = pProduktRuecknahme.findAll().get(0);

		String bemerkungNeu = "Der Kunde hat gleiches Produkt für weniger Geld gefunden.";
		erste.setBemerkung(bemerkungNeu);

		pProduktRuecknahme.update(erste);

		ProduktRuecknahme produktRuecknahmeFromDb = pProduktRuecknahme.findById(erste.getId());
		assertNotNull(produktRuecknahmeFromDb);

		assertTrue(bemerkungNeu.equals(produktRuecknahmeFromDb.getBemerkung()));

	}

	@Test
	public final void testDelete() throws Exception {

		init();
		assertTrue(pProduktRuecknahme.findAll().size() == InitHelper.INIT_SIZE_PRODUKT_RUECKNAHME);

		ProduktRuecknahme erste = pProduktRuecknahme.findAll().get(0);
		pProduktRuecknahme.delete(erste);
		assertTrue(pProduktRuecknahme.findAll().size() == InitHelper.INIT_SIZE_PRODUKT_RUECKNAHME - 1);
	}

	@Test
	public final void testDeleteById() throws Exception {

		init();
		assertTrue(pProduktRuecknahme.findAll().size() == InitHelper.INIT_SIZE_PRODUKT_RUECKNAHME);

		ProduktRuecknahme erste = pProduktRuecknahme.findAll().get(0);
		pProduktRuecknahme.deleteById(erste.getId());
		assertTrue(pProduktRuecknahme.findAll().size() == InitHelper.INIT_SIZE_PRODUKT_RUECKNAHME - 1);
	}

	@Test
	public final void testFindById() throws Exception {

		init();
		assertTrue(pProduktRuecknahme.findAll().size() == InitHelper.INIT_SIZE_PRODUKT_RUECKNAHME);

		ProduktRuecknahme erste = pProduktRuecknahme.findAll().get(0);
		assertTrue(pProduktRuecknahme.findById(erste.getId()).equals(erste));
	}

	@Test
	public final void testFindByDatum() throws Exception {

		init();
		assertTrue(pProduktRuecknahme.findAll().size() == InitHelper.INIT_SIZE_PRODUKT_RUECKNAHME);

		ProduktRuecknahme erste = pProduktRuecknahme.findAll().get(0);
		assertTrue(pProduktRuecknahme.findByDatum(erste.getDatum()).get(0).equals(erste));
	}

	@Test
	public final void testFindByProduktTyp() throws Exception {

		init();
		assertTrue(pProduktRuecknahme.findAll().size() == InitHelper.INIT_SIZE_PRODUKT_RUECKNAHME);

		ProduktRuecknahme erste = pProduktRuecknahme.findAll().get(0);

		assertTrue(pProduktRuecknahme.findByProduktTyp(erste.getProduktTyp()).get(0).equals(erste));
	}

	@Test
	public final void testFindAll() throws Exception {
		init();
		assertTrue(pProduktRuecknahme.findAll().size() == InitHelper.INIT_SIZE_PRODUKT_RUECKNAHME);
	}

}
