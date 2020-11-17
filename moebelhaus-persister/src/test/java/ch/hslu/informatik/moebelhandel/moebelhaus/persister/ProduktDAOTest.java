package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Produkt;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.ProduktDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.ProduktTypDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.InitHelper;

public class ProduktDAOTest {

	private static ProduktDAO pProdukt = new ProduktDAOImpl();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InitHelper.resetDb();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		InitHelper.deleteAllProdukt();
		InitHelper.deleteAllProduktTyp();
		InitHelper.deleteAllLieferant();
	}

	@Before
	public void setUp() throws Exception {
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
	}

	@Test
	public final void testSave() throws Exception {
		init();
		assertTrue(pProdukt.findAll().size() == InitHelper.INIT_SIZE_PRODUKT);
	}

	@Test
	public final void testUpdate() throws Exception {

		init();
		assertTrue(pProdukt.findAll().size() == InitHelper.INIT_SIZE_PRODUKT);

		int size = pProdukt.findAll().size();
		Produkt lastProdukt = pProdukt.findAll().get(size - 1);

		long code = lastProdukt.getCode();

		long codeNeu = 963852;

		lastProdukt.setCode(codeNeu);

		pProdukt.update(lastProdukt);

		Produkt produktFromDb = pProdukt.findAll().get(size - 1);
		assertFalse(produktFromDb.getCode() == code);
		assertTrue(produktFromDb.getCode() == codeNeu);
	}

	@Test
	public final void testDelete() throws Exception {

		init();
		assertTrue(pProdukt.findAll().size() == InitHelper.INIT_SIZE_PRODUKT);

		int size = pProdukt.findAll().size();

		/* Das letze Produkt löschen */
		Produkt lastProdukt = pProdukt.findAll().get(size - 1);
		pProdukt.delete(lastProdukt);

		assertTrue(pProdukt.findAll().size() == size - 1);

	}

	@Test
	public final void testDeleteById() throws Exception {

		init();
		assertTrue(pProdukt.findAll().size() == InitHelper.INIT_SIZE_PRODUKT);

		int size = pProdukt.findAll().size();

		/* Das letze Produkt löschen */
		Produkt lastProdukt = pProdukt.findAll().get(size - 1);
		pProdukt.deleteById(lastProdukt.getId());

		assertTrue(pProdukt.findAll().size() == size - 1);

	}

	@Test
	public final void testFindByProduktTyp() throws Exception {

		init();
		assertTrue(pProdukt.findAll().size() == InitHelper.INIT_SIZE_PRODUKT);

		/* Alle Produkte vom Typ "Gartentisch" holen */
		ProduktTyp gartenTisch = new ProduktTypDAOImpl().findByName("Gartentisch").get(0);
		assertNotNull(gartenTisch);
		List<Produkt> gTischListe = pProdukt.findByProduktTyp(gartenTisch);

		assertTrue(gTischListe.size() == 3);
	}

	@Test
	public final void testFindByCode() throws Exception {

		init();
		assertTrue(pProdukt.findAll().size() == InitHelper.INIT_SIZE_PRODUKT);

		int size = pProdukt.findAll().size();
		Produkt lastProdukt = pProdukt.findAll().get(size - 1);

		long code = lastProdukt.getCode();

		Produkt produktNachCode = pProdukt.findByCode(code);

		assertNotNull(produktNachCode);
		assertTrue(produktNachCode.equals(lastProdukt));
	}

	@Test
	public final void testFindById() throws Exception {

		init();
		assertTrue(pProdukt.findAll().size() == InitHelper.INIT_SIZE_PRODUKT);

		int size = pProdukt.findAll().size();
		Produkt lastProdukt = pProdukt.findAll().get(size - 1);

		long id = lastProdukt.getId();

		Produkt produktNachId = pProdukt.findById(id);

		assertNotNull(produktNachId);
		assertTrue(produktNachId.equals(lastProdukt));
	}

	@Test
	public final void testFindAll() throws Exception {

		init();
		assertTrue(pProdukt.findAll().size() == InitHelper.INIT_SIZE_PRODUKT);
	}

}
