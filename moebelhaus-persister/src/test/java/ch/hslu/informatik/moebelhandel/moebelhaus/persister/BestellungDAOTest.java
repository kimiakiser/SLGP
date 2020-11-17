package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Bestellung;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.BestellungDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.InitHelper;

public class BestellungDAOTest {

	private static BestellungDAO pBestellungDAO = new BestellungDAOImpl();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InitHelper.resetDb();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		InitHelper.deleteAllBestellung();
		InitHelper.deleteAllMoebelhaus();
		InitHelper.deleteAllLager();
		InitHelper.deleteAllRegal();
		InitHelper.deleteAllProduktTyp();
		InitHelper.deleteAllLieferant();
	}

	@Before
	public void setUp() throws Exception {
		InitHelper.deleteAllBestellung();
		InitHelper.deleteAllMoebelhaus();
		InitHelper.deleteAllLager();
		InitHelper.deleteAllRegal();
		InitHelper.deleteAllProduktTyp();
		InitHelper.deleteAllLieferant();
	}

	@After
	public void tearDown() throws Exception {
	}

	private void init() throws Exception {

		InitHelper.initLieferant();
		InitHelper.initProduktTyp();
		InitHelper.initRegal(18);
		InitHelper.initLager(3);
		InitHelper.initMoebelhaus(3);
		InitHelper.initBestellung();
	}

	@Test
	public final void testSave() throws Exception {

		init();
		assertTrue(pBestellungDAO.findAll().size() == InitHelper.INIT_SIZE_BESTELLUNG);
	}

	@Test
	public final void testUpdate() throws Exception {

		init();
		assertTrue(pBestellungDAO.findAll().size() == InitHelper.INIT_SIZE_BESTELLUNG);

		Bestellung b = pBestellungDAO.findAll().get(0);
		assertTrue(b.getBestellungPositionListe().size() == 3);

		/* Die erste BestellungPosition wird gelöscht */
		b.getBestellungPositionListe().remove(0);

		/* Updaten */
		pBestellungDAO.update(b);

		/* Kontrollieren */
		assertTrue(pBestellungDAO.findAll().get(0).getBestellungPositionListe().size() == 2);
	}

	@Test
	public final void testDelete() throws Exception {

		init();
		assertTrue(pBestellungDAO.findAll().size() == InitHelper.INIT_SIZE_BESTELLUNG);

		Bestellung b = pBestellungDAO.findAll().get(0);

		pBestellungDAO.delete(b);
		assertTrue(pBestellungDAO.findAll().size() == InitHelper.INIT_SIZE_BESTELLUNG - 1);
	}

	@Test
	public final void testFindByDatum() throws Exception {

		init();
		assertTrue(pBestellungDAO.findAll().size() == InitHelper.INIT_SIZE_BESTELLUNG);

		Bestellung b = pBestellungDAO.findAll().get(0);

		GregorianCalendar datum = b.getDatum();

		assertTrue(b.equals(pBestellungDAO.findByDatum(datum).get(0)));

	}

	@Test
	public final void testFindById() throws Exception {

		init();
		assertTrue(pBestellungDAO.findAll().size() == InitHelper.INIT_SIZE_BESTELLUNG);

		Bestellung b = pBestellungDAO.findAll().get(0);

		long id = b.getId();

		assertTrue(b.equals(pBestellungDAO.findById(id)));
	}

	@Test
	public final void testFindAll() throws Exception {

		init();
		assertTrue(pBestellungDAO.findAll().size() == InitHelper.INIT_SIZE_BESTELLUNG);
	}

}
