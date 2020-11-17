package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Regal;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Tablar;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.RegalDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.TablarDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.InitHelper;

public class RegalDAOTest {

	private static RegalDAO pRegal = new RegalDAOImpl();
	private static TablarDAO pTablar = new TablarDAOImpl();

	private Regal r = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InitHelper.resetDb();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		InitHelper.deleteAllRegal();
	}

	@Before
	public void setUp() throws Exception {
		InitHelper.deleteAllRegal();
	}

	@After
	public void tearDown() throws Exception {
	}

	/* Init-Methode */
	private void init() throws Exception {
		r = InitHelper.initRegal(1).get(0);
	}

	@Test
	public void testSave() throws Exception {

		Regal r = new Regal(12);
		r.setBezeichnung("R" + r.getNummer());

		r.getTablarListe().add(new Tablar("A"));
		r.getTablarListe().add(new Tablar("B"));
		r.getTablarListe().add(new Tablar("C"));
		r.getTablarListe().add(new Tablar("D"));
		r.getTablarListe().add(new Tablar("E"));

		for (Tablar t : r.getTablarListe()) {
			pTablar.save(t);
		}

		pRegal.save(r);

		Regal rFromDb = pRegal.findById(r.getId());
		assertNotNull(rFromDb);
		assertTrue(rFromDb.getTablarListe().size() == 5);

	}

	@Test
	public void testUpdate() throws Exception {

		init();

		List<Tablar> tListe = r.getTablarListe();
		assertTrue(tListe.size() == 12);

		/* Regal bekommt eine neue Nummer */
		String bezeichnungNeu = "R25";
		r.setBezeichnung(bezeichnungNeu);
		r = pRegal.update(r);

		Regal regalFromDb = pRegal.findById(r.getId());
		assertTrue(bezeichnungNeu.equals(regalFromDb.getBezeichnung()));

		/* Tablar 'TD0' entfernen */
		r.getTablarListe().remove(3);

		r = pRegal.update(r);

		assertTrue(r.getTablarListe().size() == 11);
	}

	@Test
	public void testFindByBezeichnung() throws Exception {

		init();

		Regal regalFromDb = pRegal.findByBezeichnung(r.getBezeichnung());

		assertNotNull(regalFromDb);
	}

	@Test
	public void testDelete() throws Exception {

		init();

		List<Tablar> tListe = r.getTablarListe();
		assertTrue(tListe.size() == 12);

		pRegal.delete(r);
		assertTrue(pRegal.findAll().isEmpty());

	}

	@Test
	public void testDeleteById() throws Exception {

		init();

		List<Tablar> tListe = r.getTablarListe();
		assertTrue(tListe.size() == 12);

		pRegal.deleteById(r.getId());
		assertTrue(pRegal.findAll().isEmpty());
	}

	@Test
	public void testFindById() throws Exception {

		init();

		Regal regalFromDb = pRegal.findById(r.getId());

		assertNotNull(regalFromDb);
		assertTrue(regalFromDb.equals(r));
	}

	@Test
	public void testFindAll() throws Exception {

		init();

		List<Tablar> tListe = r.getTablarListe();
		assertTrue(tListe.size() == 12);
	}

}
