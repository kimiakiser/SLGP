package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lieferung;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.LieferungDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.InitHelper;

public class LieferungDAOTest {

	private static LieferungDAO pLieferung = new LieferungDAOImpl();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InitHelper.resetDb();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		InitHelper.deleteAllLieferung();
		InitHelper.deleteAllLieferungPosition();
		InitHelper.deleteAllProduktTyp();
		InitHelper.deleteAllLieferant();
		InitHelper.deleteAllMoebelhaus();
		InitHelper.deleteAllLager();
		InitHelper.deleteAllRegal();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		InitHelper.deleteAllLieferung();
		InitHelper.deleteAllLieferungPosition();
		InitHelper.deleteAllProduktTyp();
		InitHelper.deleteAllLieferant();
		InitHelper.deleteAllMoebelhaus();
		InitHelper.deleteAllLager();
		InitHelper.deleteAllRegal();
	}

	private void init() throws Exception {
		InitHelper.initRegal(18);
		InitHelper.initLager(InitHelper.INIT_SIZE_LAGER);
		InitHelper.initMoebelhaus(InitHelper.INIT_SIZE_MOEBELHAUS);
		InitHelper.initLieferant();
		InitHelper.initProduktTyp();
		InitHelper.initLieferungPosition();
		InitHelper.initLieferung();
	}

	@Test
	public final void testSave() throws Exception {

		init();
		assertTrue(pLieferung.findAll().size() == InitHelper.INIT_SIZE_LIEFERUNG);
	}

	@Test
	public final void testUpdate() throws Exception {

		init();
		assertTrue(pLieferung.findAll().size() == InitHelper.INIT_SIZE_LIEFERUNG);

		Lieferung erste = pLieferung.findAll().get(0);

		int anzahlLiefPos = erste.getLieferungPositionListe().size();

		/* Eine LieferungPosition löschen */
		erste.getLieferungPositionListe().remove(0);

		pLieferung.update(erste);

		/* Kontrollieren */
		assertTrue(pLieferung.findById(erste.getId()).getLieferungPositionListe().size() == anzahlLiefPos - 1);
	}

	@Test
	public final void testDelete() throws Exception {

		init();
		assertTrue(pLieferung.findAll().size() == InitHelper.INIT_SIZE_LIEFERUNG);

		Lieferung erste = pLieferung.findAll().get(0);
		pLieferung.delete(erste);
		assertTrue(pLieferung.findAll().size() == InitHelper.INIT_SIZE_LIEFERUNG - 1);
	}

	@Test
	public final void testDeleteById() throws Exception {

		init();
		assertTrue(pLieferung.findAll().size() == InitHelper.INIT_SIZE_LIEFERUNG);

		Lieferung erste = pLieferung.findAll().get(0);
		pLieferung.deleteById(erste.getId());
		assertTrue(pLieferung.findAll().size() == InitHelper.INIT_SIZE_LIEFERUNG - 1);
	}

	@Test
	public final void testFindById() throws Exception {

		init();
		assertTrue(pLieferung.findAll().size() == InitHelper.INIT_SIZE_LIEFERUNG);
		Lieferung erste = pLieferung.findAll().get(0);
		assertTrue(pLieferung.findById(erste.getId()).equals(erste));
	}

	@Test
	public final void testFindByDatum() throws Exception {

		init();
		assertTrue(pLieferung.findAll().size() == InitHelper.INIT_SIZE_LIEFERUNG);
		Lieferung erste = pLieferung.findAll().get(0);
		assertTrue(pLieferung.findByDatum(erste.getDatum()).get(0).equals(erste));
	}

	@Test
	public final void testFindByFiliale() throws Exception {

		init();
		assertTrue(pLieferung.findAll().size() == InitHelper.INIT_SIZE_LIEFERUNG);
		Lieferung erste = pLieferung.findAll().get(0);
		assertTrue(pLieferung.findByFiliale(erste.getFiliale()).get(0).equals(erste));
	}

	@Test
	public final void testFindAll() throws Exception {
		init();
		assertTrue(pLieferung.findAll().size() == InitHelper.INIT_SIZE_LIEFERUNG);
	}

}
