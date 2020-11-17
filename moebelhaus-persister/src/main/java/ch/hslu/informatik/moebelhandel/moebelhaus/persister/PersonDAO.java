package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import java.util.List;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Person;

/**
 * Interface für Persistierung von Person-Entities.
 * 
 * @version 1.0
 * @author jsucur
 * 
 */
public interface PersonDAO extends GenericPersisterDAO<Person> {

    /**
     * Lieferat alle Personen für den übergebenen Nachnamen zurück, falls welche
     * vorhanden, sonst eine leere Liste.
     * 
     * @param nachname
     * @return
     * @throws Exception
     */
    List<Person> findByNachname(String nachname) throws Exception;

    /**
     * Lieferat alle Personen für den übergebenen Vornamen zurück, falls welche
     * vorhanden, sonst eine leere Liste.
     * 
     * @param vorname
     * @return
     * @throws Exception
     */
    List<Person> findByVorname(String vorname) throws Exception;

    /**
     * Lieferat alle Personen für die übergebenen Nach- und Vorname zurück,
     * falls welche vorhanden, sonst eine leere Liste.
     * 
     * @param nachname
     * @param vorname
     * @return
     * @throws Exception
     */
    List<Person> findByNachnameUndVorname(String nachname, String vorname) throws Exception;

    /**
     * Lieferat die Person für das übergebene Email zurück.
     * 
     * @param email
     * @return
     * @throws Exception
     */
    Person findByEmail(String email) throws Exception;
}
