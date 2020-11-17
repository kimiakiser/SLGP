package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Kontakt;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Moebelhaus;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.MoebelhausDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.InitHelper;

public class MoebelhausDAOTest {

	private static MoebelhausDAO pMoebelhaus = new MoebelhausDAOImpl();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InitHelper.resetDb();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		InitHelper.deleteAllMoebelhaus();
		InitHelper.deleteAllLager();
	}

	@Before
	public void setUp() throws Exception {
		InitHelper.deleteAllMoebelhaus();
		InitHelper.deleteAllLager();
	}

	@After
	public void tearDown() throws Exception {
	}

	public void init() throws Exception {
		InitHelper.initRegal(18);
		InitHelper.initLager(3);
		InitHelper.initMoebelhaus(3);
	}

	@Test
	public final void testSave() throws Exception {
		init();
		assertTrue(pMoebelhaus.findAll().size() == InitHelper.INIT_SIZE_MOEBELHAUS);
	}

	@Test
	public final void testUpdate() throws Exception {
		init();
		assertTrue(pMoebelhaus.findAll().size() == InitHelper.INIT_SIZE_MOEBELHAUS);

		String emailNeu = "test-email@sunrise.ch";
		String telefonNeu = "079 888 88 88";

		Moebelhaus moebelhausUri = pMoebelhaus.findByMoebelhausCode("FILIALE_UR_01");
		moebelhausUri.setKontakt(new Kontakt(emailNeu, telefonNeu));
		pMoebelhaus.update(moebelhausUri);

		Moebelhaus filialeFromDb = pMoebelhaus.findByMoebelhausCode("FILIALE_UR_01");
		assertTrue(filialeFromDb.getKontakt().getEmail().equals(emailNeu));
		assertTrue(filialeFromDb.getKontakt().getTelefon().equals(telefonNeu));
	}

	@Test
	public final void testDelete() throws Exception {
		init();
		assertTrue(pMoebelhaus.findAll().size() == InitHelper.INIT_SIZE_MOEBELHAUS);

		/* Uri-Filiale löschen */
		pMoebelhaus.delete(pMoebelhaus.findByMoebelhausCode("FILIALE_UR_01"));
		assertTrue(pMoebelhaus.findAll().size() == InitHelper.INIT_SIZE_MOEBELHAUS - 1);
	}

	@Test
	public final void testDeleteById() throws Exception {
		init();
		assertTrue(pMoebelhaus.findAll().size() == InitHelper.INIT_SIZE_MOEBELHAUS);

		/* Uri-Filiale löschen */
		pMoebelhaus.deleteById(pMoebelhaus.findByMoebelhausCode("FILIALE_UR_01").getId());
		assertTrue(pMoebelhaus.findAll().size() == InitHelper.INIT_SIZE_MOEBELHAUS - 1);
	}

	@Test
	public final void testFindByName() throws Exception {

		init();
		assertTrue(pMoebelhaus.findAll().size() == InitHelper.INIT_SIZE_MOEBELHAUS);

		/* Uri-Moebelhaus holen */
		Moebelhaus uriMoebelhaus = pMoebelhaus.findByMoebelhausCode("FILIALE_UR_01");

		String name = uriMoebelhaus.getName();

		Moebelhaus filialeNachName = pMoebelhaus.findByName(name);

		assertTrue(uriMoebelhaus.equals(filialeNachName));
	}

	@Test
	public final void testFindByMoebelhausCode() throws Exception {

		init();
		assertTrue(pMoebelhaus.findAll().size() == InitHelper.INIT_SIZE_MOEBELHAUS);

		/* Erste Filiale aus der Liste holen */
		Moebelhaus erstesMoebelhaus = pMoebelhaus.findAll().get(0);

		String moebelhausCode = erstesMoebelhaus.getMoebelhausCode();

		Moebelhaus moebelhausNachCode = pMoebelhaus.findByMoebelhausCode(moebelhausCode);

		assertTrue(erstesMoebelhaus.equals(moebelhausNachCode));
	}

	@Test
	public final void testFindById() throws Exception {

		init();
		assertTrue(pMoebelhaus.findAll().size() == InitHelper.INIT_SIZE_MOEBELHAUS);

		/* Erstes Moebelhaus aus der Liste holen */
		Moebelhaus erstesMoebelhaus = pMoebelhaus.findAll().get(0);

		long id = erstesMoebelhaus.getId();

		Moebelhaus moebelhausNachId = pMoebelhaus.findById(id);

		assertTrue(erstesMoebelhaus.equals(moebelhausNachId));
	}

	@Test
	public final void testFindAll() throws Exception {
		init();
		assertTrue(pMoebelhaus.findAll().size() == InitHelper.INIT_SIZE_MOEBELHAUS);
	}

}
