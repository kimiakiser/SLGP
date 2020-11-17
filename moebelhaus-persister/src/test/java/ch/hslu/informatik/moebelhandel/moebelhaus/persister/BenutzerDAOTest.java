package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.RolleTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.BenutzerDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.InitHelper;

public class BenutzerDAOTest {

	private static BenutzerDAO pBenutzer = new BenutzerDAOImpl();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InitHelper.resetDb();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		InitHelper.deleteAllBenutzer();
	}

	@Before
	public void setUp() throws Exception {
		InitHelper.deleteAllBenutzer();
	}

	@After
	public void tearDown() throws Exception {
	}

	private void init() throws Exception {
		InitHelper.initBenutzer();
	}

	@Test
	public final void testSave() throws Exception {

		init();
		assertTrue(pBenutzer.findAll().size() == InitHelper.INIT_SIZE_BENUTZER);
	}

	@Test
	public final void testUpdate() throws Exception {

		init();
		assertTrue(pBenutzer.findAll().size() == InitHelper.INIT_SIZE_BENUTZER);

		/* Den ersten Benutzer holen */
		Benutzer b = pBenutzer.findAll().get(0);
		long id = b.getId();

		/* Rolle aendern: er soll ADMINSTRATOR werden */
		b.setRolle(RolleTyp.ADMINISTRATOR);

		/* Updaten */
		pBenutzer.update(b);

		b = null;

		/* Benutzer nach Id suchen */
		b = pBenutzer.findById(id);

		assertTrue(b.getRolle().equals(RolleTyp.ADMINISTRATOR));

	}

	@Test
	public final void testDelete() throws Exception {

		init();
		assertTrue(pBenutzer.findAll().size() == InitHelper.INIT_SIZE_BENUTZER);

		/* Den ersten Benutzer holen */
		Benutzer b = pBenutzer.findAll().get(0);

		/* Den ersten Benutzer löschen und kontrollieren */
		pBenutzer.delete(b);
		assertTrue(pBenutzer.findAll().size() == InitHelper.INIT_SIZE_BENUTZER - 1);
	}

	@Test
	public final void testDeleteById() throws Exception {

		init();
		assertTrue(pBenutzer.findAll().size() == InitHelper.INIT_SIZE_BENUTZER);

		/* Den ersten Benutzer holen */
		Benutzer b = pBenutzer.findAll().get(0);

		/* Den ersten Benutzer löschen und kontrollieren */
		pBenutzer.deleteById(b.getId());

		assertTrue(pBenutzer.findAll().size() == InitHelper.INIT_SIZE_BENUTZER - 1);
	}

	@Test
	public final void testFindByBenutzername() throws Exception {

		init();
		assertTrue(pBenutzer.findAll().size() == InitHelper.INIT_SIZE_BENUTZER);

		/* Den ersten Benutzer holen */
		Benutzer b = pBenutzer.findAll().get(0);

		String benutzername = b.getCredentials().getBenutzername();
		Benutzer bNachBenutzername = pBenutzer.findByBenutzername(benutzername);
		assertTrue(b.equals(bNachBenutzername));
	}

	@Test
	public final void testFindByRolleTyp() throws Exception {

		init();
		assertTrue(pBenutzer.findAll().size() == InitHelper.INIT_SIZE_BENUTZER);

		assertTrue(pBenutzer.findByRolleTyp(RolleTyp.FILIALE_SACHBEARBEITER).size() == 2);
		assertTrue(pBenutzer.findByRolleTyp(RolleTyp.KASSE_MITARBEITER).size() == 2);

	}

	@Test
	public final void testFindById() throws Exception {

		init();
		assertTrue(pBenutzer.findAll().size() == InitHelper.INIT_SIZE_BENUTZER);

		/* Den ersten Benutzer holen */
		Benutzer b = pBenutzer.findAll().get(0);

		long id = b.getId();
		Benutzer bNachId = pBenutzer.findById(id);
		assertTrue(b.equals(bNachId));
	}

	@Test
	public final void testFindAll() throws Exception {
		init();
		assertTrue(pBenutzer.findAll().size() == InitHelper.INIT_SIZE_BENUTZER);
	}

}
