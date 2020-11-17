package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Credentials;

/**
 * Interface für Persistierung von Credentials-Entities.
 * 
 * @version 1.0
 * @author jsucur
 * 
 */
public interface CredentialsDAO extends GenericPersisterDAO<Credentials> {
    /**
     * Liefert die Credentials für den übergebenen Benutzernamen zurück.
     * 
     * @param benutzername
     * @return
     * @throws Exception
     */
    Credentials findByBenutzername(String benutzername) throws Exception;
}
