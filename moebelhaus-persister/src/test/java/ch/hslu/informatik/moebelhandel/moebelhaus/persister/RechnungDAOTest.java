package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Produkt;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Rechnung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.RechnungPosition;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.ProduktDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.RechnungDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.InitHelper;

public class RechnungDAOTest {

	private static RechnungDAO pRechnung = new RechnungDAOImpl();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InitHelper.resetDb();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		InitHelper.deleteAllRechnung();
		InitHelper.deleteAllBenutzer();
		InitHelper.deleteAllProdukt();
		InitHelper.deleteAllProduktTyp();
		InitHelper.deleteAllLieferant();
	}

	@Before
	public void setUp() throws Exception {
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
	}

	@Test
	public final void testSave() throws Exception {

		init();
		assertTrue(pRechnung.findAll().size() == InitHelper.INIT_SIZE_RECHNUNG);
	}

	@Test
	public final void testUpdate() throws Exception {

		init();
		assertTrue(pRechnung.findAll().size() == InitHelper.INIT_SIZE_RECHNUNG);

		Rechnung erste = pRechnung.findAll().get(0);
		assertNotNull(erste);
		int anzahlRechnungPositionen = erste.getRechnungPositionListe().size();

		/* Ein weiteres Produkt verkaufen */
		Produkt p = new ProduktDAOImpl().findAll().get(7);
		erste.addRechnungPosition(new RechnungPosition(p.getTyp(), 1));

		pRechnung.update(erste);

		Rechnung rechnungFromDb = pRechnung.findById(erste.getId());

		assertTrue(rechnungFromDb.getRechnungPositionListe().size() == anzahlRechnungPositionen + 1);
	}

	@Test
	public final void testDelete() throws Exception {

		init();
		assertTrue(pRechnung.findAll().size() == InitHelper.INIT_SIZE_RECHNUNG);

		Rechnung erste = pRechnung.findAll().get(0);
		assertNotNull(erste);
		pRechnung.delete(erste);
		assertTrue(pRechnung.findAll().size() == InitHelper.INIT_SIZE_RECHNUNG - 1);
	}

	@Test
	public final void testDeleteById() throws Exception {

		init();
		assertTrue(pRechnung.findAll().size() == InitHelper.INIT_SIZE_RECHNUNG);

		Rechnung erste = pRechnung.findAll().get(0);
		assertNotNull(erste);
		pRechnung.deleteById(erste.getId());
		assertTrue(pRechnung.findAll().size() == InitHelper.INIT_SIZE_RECHNUNG - 1);
	}

	@Test
	public final void testFindById() throws Exception {

		init();
		assertTrue(pRechnung.findAll().size() == InitHelper.INIT_SIZE_RECHNUNG);

		Rechnung erste = pRechnung.findAll().get(0);
		assertNotNull(erste);
		assertTrue(pRechnung.findById(erste.getId()).equals(erste));
	}

	@Test
	public final void testFindByDatum() throws Exception {

		init();
		assertTrue(pRechnung.findAll().size() == InitHelper.INIT_SIZE_RECHNUNG);

		Rechnung erste = pRechnung.findAll().get(0);
		assertNotNull(erste);
		Rechnung rechnungFromDb = pRechnung.findByDatum(erste.getZeit()).get(0);
		assertTrue(rechnungFromDb.equals(erste));
	}

	@Test
	public final void testFindAll() throws Exception {
		init();
		assertTrue(pRechnung.findAll().size() == InitHelper.INIT_SIZE_RECHNUNG);
	}

}
