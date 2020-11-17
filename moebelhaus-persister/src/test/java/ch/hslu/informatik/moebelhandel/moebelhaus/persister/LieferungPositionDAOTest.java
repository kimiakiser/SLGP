package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.LieferungPosition;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.LieferungPositionDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.InitHelper;

public class LieferungPositionDAOTest {

	private static LieferungPositionDAO pLieferungPosition = new LieferungPositionDAOImpl();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InitHelper.resetDb();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		InitHelper.deleteAllLieferungPosition();
		InitHelper.deleteAllProduktTyp();
		InitHelper.deleteAllLieferant();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		InitHelper.deleteAllLieferungPosition();
		InitHelper.deleteAllProduktTyp();
		InitHelper.deleteAllLieferant();
	}

	private void init() throws Exception {
		InitHelper.initLieferant();
		InitHelper.initProduktTyp();
		InitHelper.initLieferungPosition();
	}

	@Test
	public final void testSave() throws Exception {

		init();
		assertTrue(pLieferungPosition.findAll().size() == InitHelper.INIT_SIZE_LIEFERUNG_POSITION);
	}

	@Test
	public final void testUpdate() throws Exception {

		init();
		assertTrue(pLieferungPosition.findAll().size() == InitHelper.INIT_SIZE_LIEFERUNG_POSITION);

		LieferungPosition ersteLiefPos = pLieferungPosition.findAll().get(0);
		long id = ersteLiefPos.getId();

		int anzahl = ersteLiefPos.getAnzahl();

		/* Anzahl verdoppeln */
		int anzahlNeu = anzahl * 2;

		ersteLiefPos.setAnzahl(anzahlNeu);

		pLieferungPosition.update(ersteLiefPos);

		/* Suchen nach Id und kontrollieren */
		ersteLiefPos = null;

		ersteLiefPos = pLieferungPosition.findById(id);

		assertTrue(ersteLiefPos.getAnzahl() == anzahlNeu);
	}

	@Test
	public final void testDelete() throws Exception {

		init();
		assertTrue(pLieferungPosition.findAll().size() == InitHelper.INIT_SIZE_LIEFERUNG_POSITION);

		LieferungPosition ersteLiefPos = pLieferungPosition.findAll().get(0);
		pLieferungPosition.delete(ersteLiefPos);
		assertTrue(pLieferungPosition.findAll().size() == InitHelper.INIT_SIZE_LIEFERUNG_POSITION - 1);

	}

	@Test
	public final void testDeleteById() throws Exception {

		init();
		assertTrue(pLieferungPosition.findAll().size() == InitHelper.INIT_SIZE_LIEFERUNG_POSITION);

		LieferungPosition ersteLiefPos = pLieferungPosition.findAll().get(0);
		pLieferungPosition.deleteById(ersteLiefPos.getId());
		assertTrue(pLieferungPosition.findAll().size() == InitHelper.INIT_SIZE_LIEFERUNG_POSITION - 1);
	}

	@Test
	public final void testFindById() throws Exception {

		init();
		assertTrue(pLieferungPosition.findAll().size() == InitHelper.INIT_SIZE_LIEFERUNG_POSITION);

		LieferungPosition ersteLiefPos = pLieferungPosition.findAll().get(0);
		long id = ersteLiefPos.getId();
		assertTrue(pLieferungPosition.findById(id).equals(ersteLiefPos));

	}

	@Test
	public final void testFindByProduktTyp() throws Exception {

		init();
		assertTrue(pLieferungPosition.findAll().size() == InitHelper.INIT_SIZE_LIEFERUNG_POSITION);

		LieferungPosition ersteLiefPos = pLieferungPosition.findAll().get(0);
		ProduktTyp typ = ersteLiefPos.getProduktTyp();
		assertTrue(pLieferungPosition.findByProduktTyp(typ).get(0).equals(ersteLiefPos));
	}

	@Test
	public final void testFindAll() throws Exception {
		init();
		assertTrue(pLieferungPosition.findAll().size() == InitHelper.INIT_SIZE_LIEFERUNG_POSITION);
	}

}
