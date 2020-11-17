package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Kontakt;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Person;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.PersonDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.InitHelper;

public class PersonDAOTest {

	private static PersonDAO pPerson = new PersonDAOImpl();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InitHelper.resetDb();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		InitHelper.deleteAllPerson();
	}

	@Before
	public void setUp() throws Exception {
		InitHelper.deleteAllPerson();
	}

	@After
	public void tearDown() throws Exception {
	}

	public void init() throws Exception {
		InitHelper.initPerson();
	}

	@Test
	public final void testSave() throws Exception {
		init();
		assertTrue(pPerson.findAll().size() == InitHelper.INIT_SIZE_PERSON);
	}

	@Test
	public final void testUpdate() throws Exception {

		init();
		assertTrue(pPerson.findAll().size() == InitHelper.INIT_SIZE_PERSON);

		int size = pPerson.findAll().size();

		Person lastPerson = pPerson.findAll().get(size - 1);

		Kontakt kontaktOrg = lastPerson.getKontakt();

		Kontakt kontaktNeu = new Kontakt("alindauer@roche.com", "041 999 99 99");

		lastPerson.setKontakt(kontaktNeu);

		pPerson.update(lastPerson);

		/* Person holen */
		Person personFromDb = pPerson.findAll().get(size - 1);

		assertFalse(kontaktOrg.equals(personFromDb.getKontakt()));
		assertTrue(kontaktNeu.getEmail().equals(personFromDb.getKontakt().getEmail()));
		assertTrue(kontaktNeu.getTelefon().equals(personFromDb.getKontakt().getTelefon()));

	}

	@Test
	public final void testDelete() throws Exception {

		init();
		assertTrue(pPerson.findAll().size() == InitHelper.INIT_SIZE_PERSON);

		int size = pPerson.findAll().size();

		Person lastPerson = pPerson.findAll().get(size - 1);

		/* Person löschen */
		pPerson.delete(lastPerson);
		assertTrue(pPerson.findAll().size() == InitHelper.INIT_SIZE_PERSON - 1);
	}

	@Test
	public final void testDeleteById() throws Exception {

		init();
		assertTrue(pPerson.findAll().size() == InitHelper.INIT_SIZE_PERSON);

		int size = pPerson.findAll().size();

		Person lastPerson = pPerson.findAll().get(size - 1);

		/* Person löschen */
		pPerson.deleteById(lastPerson.getId());
		assertTrue(pPerson.findAll().size() == InitHelper.INIT_SIZE_PERSON - 1);

	}

	@Test
	public final void testFindById() throws Exception {

		init();
		assertTrue(pPerson.findAll().size() == InitHelper.INIT_SIZE_PERSON);

		int size = pPerson.findAll().size();

		Person lastPerson = pPerson.findAll().get(size - 1);

		long id = lastPerson.getId();

		/* Person nach Id holen */
		Person personFromDb = pPerson.findById(id);
		assertTrue(lastPerson.equals(personFromDb));

	}

	@Test
	public final void testFindByNachname() throws Exception {

		init();
		assertTrue(pPerson.findAll().size() == InitHelper.INIT_SIZE_PERSON);

		int size = pPerson.findAll().size();

		Person lastPerson = pPerson.findAll().get(size - 1);

		String nachname = lastPerson.getNachname();

		List<Person> personNachNachnameListe = pPerson.findByNachname(nachname);

		assertTrue(personNachNachnameListe.contains(lastPerson));
	}

	@Test
	public final void testFindByVorname() throws Exception {

		init();
		assertTrue(pPerson.findAll().size() == InitHelper.INIT_SIZE_PERSON);

		int size = pPerson.findAll().size();

		Person lastPerson = pPerson.findAll().get(size - 1);

		String vorname = lastPerson.getVorname();

		List<Person> personNachNachnameListe = pPerson.findByVorname(vorname);

		assertTrue(personNachNachnameListe.contains(lastPerson));
	}

	@Test
	public final void testFindByNachnameUndVorname() throws Exception {

		init();
		assertTrue(pPerson.findAll().size() == InitHelper.INIT_SIZE_PERSON);

		int size = pPerson.findAll().size();

		Person lastPerson = pPerson.findAll().get(size - 1);

		String nachname = lastPerson.getNachname();
		String vorname = lastPerson.getVorname();

		List<Person> personNachNachUndVornameListe = pPerson.findByNachnameUndVorname(nachname, vorname);

		assertTrue(personNachNachUndVornameListe.contains(lastPerson));
	}

	@Test
	public final void testFindByEmail() throws Exception {

		init();
		assertTrue(pPerson.findAll().size() == InitHelper.INIT_SIZE_PERSON);

		int size = pPerson.findAll().size();

		Person lastPerson = pPerson.findAll().get(size - 1);

		String email = lastPerson.getKontakt().getEmail();

		Person personNachEmail = pPerson.findByEmail(email);

		assertTrue(personNachEmail.equals(lastPerson));
	}

	@Test
	public final void testFindAll() throws Exception {

		init();
		assertTrue(pPerson.findAll().size() == InitHelper.INIT_SIZE_PERSON);
	}

}
