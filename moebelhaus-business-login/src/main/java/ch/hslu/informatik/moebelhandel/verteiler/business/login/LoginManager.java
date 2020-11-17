package ch.hslu.informatik.moebelhandel.verteiler.business.login;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.api.LoginService;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Credentials;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.BenutzerDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.CredentialsDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.BenutzerDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.CredentialsDAOImpl;

public class LoginManager implements LoginService {

	private static Logger logger = LogManager.getLogger(LoginManager.class);

	private BenutzerDAO benutzerDAO;
	private CredentialsDAO credentialsDAO;

	public BenutzerDAO getBenutzerDAO() {

		if (benutzerDAO == null) {
			benutzerDAO = new BenutzerDAOImpl();
		}

		return benutzerDAO;
	}

	public CredentialsDAO getCredentialsDAO() {

		if (credentialsDAO == null) {
			credentialsDAO = new CredentialsDAOImpl();
		}

		return credentialsDAO;
	}

	public Benutzer login(String benutzername, String kennwort) throws Exception {

		try {

			Benutzer b = getBenutzerDAO().findByBenutzername(benutzername);

			if (b != null) {
				return kennwort.equals(b.getCredentials().getPasswort()) ? b : null;
			}

			return null;

		} catch (Exception e) {
			String msg = "Benutzer \'" + benutzername + "\' konnte nicht angemeldet werden";
			logger.error(msg, e);
			throw new Exception(msg);
		}
	}

	public boolean kennwortAendern(String benutzername, String kennwortAktuell, String kennwortNeu) throws Exception {

		try {
			Benutzer b = getBenutzerDAO().findByBenutzername(benutzername);

			if (kennwortAktuell.equals(b.getCredentials().getPasswort())) {
				Credentials c = b.getCredentials();
				c.setPasswort(kennwortNeu);
				/* Updaten */
				c = getCredentialsDAO().save(c);

				if (kennwortNeu.equals(c.getPasswort())) {
					return true;
				}

				return false;
			}
		} catch (Exception e) {
			String msg = "Kennwort des Benuzers \'" + benutzername + "\' konnte nicht geändert werden";
			logger.error(msg, e);
			throw new Exception(msg);
		}

		return false;
	}

}
