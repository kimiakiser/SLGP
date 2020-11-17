package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lieferant;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.ProduktTypDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.InitHelper;

public class ProduktTypDAOTest {

	private static ProduktTypDAO pProduktTyp = new ProduktTypDAOImpl();

	private List<ProduktTyp> liste = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InitHelper.resetDb();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		InitHelper.deleteAllProduktTyp();
		InitHelper.deleteAllLieferant();
	}

	@Before
	public void setUp() throws Exception {
		InitHelper.deleteAllProduktTyp();
		InitHelper.deleteAllLieferant();
	}

	@After
	public void tearDown() throws Exception {
	}

	private void init() throws Exception {

		List<Lieferant> lieferantListe = InitHelper.initLieferant();

		liste = new ArrayList<ProduktTyp>();

		/* Lieferant 1 */
		liste.add(new ProduktTyp("Computer-Tisch",
				"Lackierte Korpusteile in Mattlack, weiss, 3 Schubkästen mit Superweissglasblenden, B 132 T 60 H 75 cm",
				699.00, "CmpT-KSTF-CSL-440", lieferantListe.get(0)));

		liste.add(new ProduktTyp("Computer-Tisch",
				"Klarglas, Seitenteile Aluminium geschliffen, Kabelrückwand, parkettgeeignete Rollen, Belastbarkeit 40 kg, B 80 T 53 H 76 cm",
				359.00, "CmpT-KSTF-CU-MR-200-E", lieferantListe.get(0)));

		liste.add(new ProduktTyp("Schreibtisch",
				"Sheeshamholz lackiert, Schubladenfronten Kuhfell, Gestell Eisen, geschnitzte Fronten, B 116 T 60 H 78 cm",
				1099.00, "ShrT-KSTF-CU-BT-140-ER", lieferantListe.get(0)));

		/* Lieferant 2 */
		liste.add(new ProduktTyp("Gartentisch",
				"Gartentisch Oxford aus Holz, Wetterbeständig, Teakholz massiv 200 x 100 cm", 699.00, "GT-HLZ-RUM-08",
				lieferantListe.get(1)));

		liste.add(new ProduktTyp("Gartentisch",
				"Gartentisch Oskar aus Holz, Wetterbeständig, Teakholz massiv 180 x 90 cm", 799.00, "GT-HLZ-AXF-26",
				lieferantListe.get(1)));

		liste.add(new ProduktTyp("Gartenstuhl",
				"Gartenstuhl Larino aus Holz, Wetterbeständig, passend zu Tisch GT-HLZ-AXF-26, B 62 T 68 H 110 cm",
				155.00, "GSTH-HLZ-RUM-08-STH-01", lieferantListe.get(1)));

		/* Lieferant 3 */
		liste.add(new ProduktTyp("Sofa",
				"2.5er-Sofa Adela, Leder Columbia classicgrau, B 170 T 83 H 84 cm, ST 54 SB 140 SH 44 cm", 2375.00,
				"SOFA-LD-CLMB-72-1", lieferantListe.get(2)));

		liste.add(new ProduktTyp("Sofa",
				"2.5er-Sofa Classics 650, Bezug Leder siena, ohne Ledertrennungsnaht, Sitz-/Rückenkissen mit Dickfadenziernaht, Metallfuss hochglanz höhenverstellbar, B 188 T 87 H 84 cm - Sitztiefe 53 cm",
				2375.00, "SOFA-LD-IT-23-4", lieferantListe.get(2)));

		liste.add(new ProduktTyp("Sessel", "Bezug: Vintage Lederlook, B 72 T 82 H 102 cm", 490.00, "SS-CHN-UT-14-6",
				lieferantListe.get(2)));

		for (ProduktTyp t : liste) {
			pProduktTyp.save(t);
		}

	}

	@Test
	public final void testSave() throws Exception {
		init();
		assertTrue(pProduktTyp.findAll().size() == InitHelper.INIT_SIZE_PRODUKT_TYP);
	}

	@Test
	public final void testUpdate() throws Exception {

		init();
		assertTrue(pProduktTyp.findAll().size() == InitHelper.INIT_SIZE_PRODUKT_TYP);

		int size = pProduktTyp.findAll().size();

		ProduktTyp lastProduktTyp = pProduktTyp.findAll().get(size - 1);

		/* Preis wird um 100 CHF reduziert */
		double preis = lastProduktTyp.getPreis();
		double preisNeu = preis - 100;

		lastProduktTyp.setPreis(preisNeu);

		pProduktTyp.update(lastProduktTyp);

		/* Produkt neu holen */
		lastProduktTyp = null;
		lastProduktTyp = pProduktTyp.findAll().get(size - 1);
		assertTrue(lastProduktTyp.getPreis() == preisNeu);

	}

	@Test
	public final void testDelete() throws Exception {

		init();
		assertTrue(pProduktTyp.findAll().size() == InitHelper.INIT_SIZE_PRODUKT_TYP);

		for (ProduktTyp typ : pProduktTyp.findAll()) {
			pProduktTyp.delete(typ);
		}

		assertTrue(pProduktTyp.findAll().isEmpty());
	}

	@Test
	public final void testDeleteById() throws Exception {

		init();
		assertTrue(pProduktTyp.findAll().size() == InitHelper.INIT_SIZE_PRODUKT_TYP);

		for (ProduktTyp typ : pProduktTyp.findAll()) {
			pProduktTyp.deleteById(typ.getId());
		}

		assertTrue(pProduktTyp.findAll().isEmpty());
	}

	@Test
	public final void testFindByName() throws Exception {

		init();
		assertTrue(pProduktTyp.findAll().size() == InitHelper.INIT_SIZE_PRODUKT_TYP);

		List<ProduktTyp> gartenTischListe = pProduktTyp.findByName("Gartentisch");

		assertTrue(gartenTischListe.size() == 2);
	}

	@Test
	public final void testFindByCode() throws Exception {

		init();
		assertTrue(pProduktTyp.findAll().size() == InitHelper.INIT_SIZE_PRODUKT_TYP);

		ProduktTyp produktTyp = pProduktTyp.findByTypCode("CmpT-KSTF-CSL-440");
		assertNotNull(produktTyp);
	}

	@Test
	public final void testFindById() throws Exception {

		init();
		assertTrue(pProduktTyp.findAll().size() == InitHelper.INIT_SIZE_PRODUKT_TYP);

		ProduktTyp produktTypNachCode = pProduktTyp.findByTypCode("CmpT-KSTF-CSL-440");
		assertNotNull(produktTypNachCode);

		long id = produktTypNachCode.getId();

		ProduktTyp produktTypNachId = pProduktTyp.findById(id);
		assertTrue(produktTypNachCode.equals(produktTypNachId));
	}

	@Test
	public final void testFindAll() throws Exception {
		init();
		assertTrue(pProduktTyp.findAll().size() == InitHelper.INIT_SIZE_PRODUKT_TYP);
	}

}
