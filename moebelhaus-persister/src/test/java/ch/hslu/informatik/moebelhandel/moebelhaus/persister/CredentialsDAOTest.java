package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Credentials;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.CredentialsDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.InitHelper;

public class CredentialsDAOTest {

	private static CredentialsDAO pCredentials = new CredentialsDAOImpl();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InitHelper.resetDb();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		InitHelper.deleteAllCredentials();
	}

	@Before
	public void setUp() throws Exception {
		InitHelper.deleteAllCredentials();
	}

	@After
	public void tearDown() throws Exception {
	}

	private void init() throws Exception {
		InitHelper.initCredentials();
	}

	@Test
	public final void testSave() throws Exception {
		init();
		assertTrue(pCredentials.findAll().size() == InitHelper.INIT_SIZE_CREDENTIALS);
	}

	@Test
	public final void testUpdate() throws Exception {

		init();
		assertTrue(pCredentials.findAll().size() == InitHelper.INIT_SIZE_CREDENTIALS);

		Credentials ersteCredentials = pCredentials.findAll().get(0);

		String benutzername = ersteCredentials.getBenutzername();

		String passwordNeu = "password_neu";

		ersteCredentials.setPasswort(passwordNeu);

		pCredentials.update(ersteCredentials);

		Credentials credentialsFromDb = pCredentials.findByBenutzername(benutzername);

		assertTrue(passwordNeu.equals(credentialsFromDb.getPasswort()));
	}

	@Test
	public final void testDelete() throws Exception {

		init();
		assertTrue(pCredentials.findAll().size() == InitHelper.INIT_SIZE_CREDENTIALS);

		Credentials ersteCredentials = pCredentials.findAll().get(0);
		pCredentials.delete(ersteCredentials);

		assertTrue(pCredentials.findAll().size() == InitHelper.INIT_SIZE_CREDENTIALS - 1);
	}

	@Test
	public final void testDeleteById() throws Exception {

		init();
		assertTrue(pCredentials.findAll().size() == InitHelper.INIT_SIZE_CREDENTIALS);

		Credentials ersteCredentials = pCredentials.findAll().get(0);
		pCredentials.deleteById(ersteCredentials.getId());

		assertTrue(pCredentials.findAll().size() == InitHelper.INIT_SIZE_CREDENTIALS - 1);
	}

	@Test
	public final void testFindById() throws Exception {

		init();
		assertTrue(pCredentials.findAll().size() == InitHelper.INIT_SIZE_CREDENTIALS);

		Credentials ersteCredentials = pCredentials.findAll().get(0);

		long id = ersteCredentials.getId();

		assertTrue(ersteCredentials.equals(pCredentials.findById(id)));
	}

	@Test
	public final void testFindByBenutzername() throws Exception {

		init();
		assertTrue(pCredentials.findAll().size() == InitHelper.INIT_SIZE_CREDENTIALS);

		Credentials ersteCredentials = pCredentials.findAll().get(0);

		String benutzername = ersteCredentials.getBenutzername();

		assertTrue(ersteCredentials.equals(pCredentials.findByBenutzername(benutzername)));
	}

	@Test
	public final void testFindAll() throws Exception {
		init();
		assertTrue(pCredentials.findAll().size() == InitHelper.INIT_SIZE_CREDENTIALS);
	}

}
