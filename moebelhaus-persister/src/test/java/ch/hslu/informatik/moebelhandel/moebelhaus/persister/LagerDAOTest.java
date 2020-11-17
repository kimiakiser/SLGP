package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lager;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Regal;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.LagerDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.InitHelper;

public class LagerDAOTest {

	private static LagerDAO pLager = new LagerDAOImpl();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InitHelper.resetDb();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		InitHelper.deleteAllLager();
		InitHelper.deleteAllRegal();
	}

	@Before
	public void setUp() throws Exception {
		InitHelper.deleteAllLager();
		InitHelper.deleteAllRegal();

	}

	@After
	public void tearDown() throws Exception {
	}

	public void init() throws Exception {
		InitHelper.initRegal(18);
		InitHelper.initLager(3);
	}

	@Test
	public final void testSave() throws Exception {
		init();
		assertTrue(pLager.findAll().size() == InitHelper.INIT_SIZE_LAGER);
	}

	@Test
	public final void testUpdate() throws Exception {

		init();
		assertTrue(pLager.findAll().size() == InitHelper.INIT_SIZE_LAGER);

		/*
		 * Lager 'lager C' hat zurzeit 8 Regale: zwei werden entferent und dem
		 * 'lager B' hinzugefügt
		 */
		Lager lagerB = pLager.findByName("Lager B");
		Lager lagerC = pLager.findByName("Lager C");

		assertTrue(lagerB.getRegalListe().size() == 6);
		assertTrue(lagerC.getRegalListe().size() == 8);

		Regal regalNr8 = lagerC.getRegalListe().get(lagerC.getRegalListe().size() - 1);
		Regal regalNr7 = lagerC.getRegalListe().get(lagerC.getRegalListe().size() - 2);

		/* Regale aus dem 'lager C' entferenen */
		lagerC.removeRegal(regalNr8);
		lagerC.removeRegal(regalNr7);

		/* Updaten */
		lagerC = pLager.update(lagerC);
		assertTrue(lagerC.getRegalListe().size() == 6);

		/* Regale in 'lager B' einfügen */
		lagerB.addRegal(regalNr7);
		lagerB.addRegal(regalNr8);

		/* Updaten */
		lagerB = pLager.update(lagerB);
		assertTrue(lagerB.getRegalListe().size() == 8);

	}

	@Test
	public final void testDelete() throws Exception {

		init();
		assertTrue(pLager.findAll().size() == InitHelper.INIT_SIZE_LAGER);

		/*
		 * Lager 'lager C' und 'lager B' werde gelöscht
		 */
		Lager lagerB = pLager.findByName("Lager B");
		Lager lagerC = pLager.findByName("Lager C");

		pLager.delete(lagerB);
		pLager.delete(lagerC);

		assertTrue(pLager.findAll().size() == InitHelper.INIT_SIZE_LAGER - 2);
	}

	@Test
	public final void testDeleteById() throws Exception {

		init();
		assertTrue(pLager.findAll().size() == InitHelper.INIT_SIZE_LAGER);

		/*
		 * Lager 'lager C' und 'lager B' werde gelöscht
		 */
		Lager lagerB = pLager.findByName("Lager B");
		Lager lagerC = pLager.findByName("Lager C");

		pLager.deleteById(lagerB.getId());
		pLager.deleteById(lagerC.getId());

		assertTrue(pLager.findAll().size() == InitHelper.INIT_SIZE_LAGER - 2);
	}

	@Test
	public final void testFindByName() throws Exception {

		init();
		assertTrue(pLager.findAll().size() == InitHelper.INIT_SIZE_LAGER);

		/*
		 * Lager 'lager C' und 'lager B' holen
		 */
		Lager lagerB = pLager.findByName("Lager B");
		Lager lagerC = pLager.findByName("Lager C");

		assertNotNull(lagerB);
		assertNotNull(lagerC);
	}

	@Test
	public final void testFindById() throws Exception {

		init();
		assertTrue(pLager.findAll().size() == InitHelper.INIT_SIZE_LAGER);

		/*
		 * Lager 'lager C' und 'lager B' holen
		 */
		Lager lagerB = pLager.findByName("Lager B");
		Lager lagerC = pLager.findByName("Lager C");

		long idB = lagerB.getId();
		long idC = lagerC.getId();

		assertTrue(lagerB.equals(pLager.findById(idB)));
		assertTrue(lagerC.equals(pLager.findById(idC)));
	}

	@Test
	public final void testFindAll() throws Exception {
		init();
		assertTrue(pLager.findAll().size() == InitHelper.INIT_SIZE_LAGER);
	}

}
