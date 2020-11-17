package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Tablar;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.TablarDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.InitHelper;

public class TablarDAOTest {

	private static TablarDAO pTablar = new TablarDAOImpl();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InitHelper.resetDb();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		for (Tablar t : pTablar.findAll()) {
			pTablar.delete(t);
		}
	}

	@Before
	public void setUp() throws Exception {
		List<Tablar> liste = pTablar.findAll();
		for (Tablar t : liste) {
			pTablar.delete(t);
		}
	}

	@After
	public void tearDown() {

	}

	@Test
	public void testSaveTablar() throws Exception {
		Tablar tablar = new Tablar("R12-TA");
		pTablar.save(tablar);
		assertTrue(pTablar.findAll().size() == 1);
	}

	@Test
	public void testSaveTablarWithSameName() throws Exception {
		String bezeichnung = "R12-TA";

		Tablar tablarA = new Tablar(bezeichnung);
		pTablar.save(tablarA);
		assertTrue(pTablar.findAll().size() == 1);

		Tablar tablarB = new Tablar(bezeichnung);
		pTablar.save(tablarB);
		assertTrue(pTablar.findAll().size() == 2);
	}

	@Test
	public void testUpdateTablar() throws Exception {

		String bezeichnung = "R12-TA";

		Tablar tablar = new Tablar(bezeichnung);
		pTablar.save(tablar);
		assertTrue(pTablar.findAll().size() == 1);
		assertTrue(bezeichnung.equals(pTablar.findAll().get(0).getBezeichnung()));

		/* Bezeichnung ändern */
		String bezeichnungNeu = "R12-TB";
		Tablar tablarFromDb = pTablar.findAll().get(0);

		tablarFromDb.setBezeichnung(bezeichnungNeu);
		pTablar.update(tablarFromDb);
		assertTrue(pTablar.findAll().size() == 1);
		assertTrue(bezeichnungNeu.equals(pTablar.findAll().get(0).getBezeichnung()));
	}

	@Test
	public void testDeleteTablar() throws Exception {

		String bezeichnung = "R12-TA";

		Tablar tablar = new Tablar(bezeichnung);
		pTablar.save(tablar);
		assertTrue(pTablar.findAll().size() == 1);

		pTablar.delete(tablar);
		assertTrue(pTablar.findAll().isEmpty());
	}

	@Test
	public void testDeleteTablarById() throws Exception {

		String bezeichnung = "R12-TA";

		Tablar tablar = new Tablar(bezeichnung);
		pTablar.save(tablar);
		assertTrue(pTablar.findAll().size() == 1);

		pTablar.deleteById(tablar.getId());
		assertTrue(pTablar.findAll().isEmpty());
	}

	@Test
	public void testFindTablarById() throws Exception {

		String bezeichnung = "R12-TA";

		Tablar tablar = new Tablar(bezeichnung);
		pTablar.save(tablar);
		assertTrue(pTablar.findAll().size() == 1);

		long id = tablar.getId();

		Tablar tablarFromDb = pTablar.findById(id);
		assertNotNull(tablarFromDb);
		assertTrue(tablar.equals(tablarFromDb));
	}

	@Test
	public void testFindAllTablar() throws Exception {

		String bezeichnungA = "R12-TA";
		String bezeichnungB = "R12-TB";

		Tablar tablarA = new Tablar(bezeichnungA);
		pTablar.save(tablarA);
		assertTrue(pTablar.findAll().size() == 1);

		Tablar tablarB = new Tablar(bezeichnungB);
		pTablar.save(tablarB);
		assertTrue(pTablar.findAll().size() == 2);

	}
}
