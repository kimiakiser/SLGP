package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ProduktRuecknahmeDAOTest.class, RechnungDAOTest.class, LieferungDAOTest.class,
		LieferungPositionDAOTest.class, BestellungDAOTest.class, 
		BenutzerDAOTest.class, CredentialsDAOTest.class, MoebelhausDAOTest.class, LagerDAOTest.class, PersonDAOTest.class,
		ProduktDAOTest.class, ProduktTypDAOTest.class, LieferantDAOTest.class, RegalDAOTest.class,
		TablarDAOTest.class })
public class AllTests {

}
