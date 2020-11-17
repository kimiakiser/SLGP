package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import java.util.List;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.RolleTyp;

/**
 * Interface für Persistierung von Benutzer-Entities.
 *
 * @version 1.0
 * @author jsucur
 *
 */
public interface BenutzerDAO extends GenericPersisterDAO<Benutzer> {

    /**
     * Liefert den Benutzer zurück, dessen Benutzername übergeben wurde.
     *
     * @param benutzername
     * @return
     * @throws Exception
     */
    Benutzer findByBenutzername(String benutzername) throws Exception;

    /**
     * Liefert alle Benutzer zurück, welche die Rolle vom übergebenen Typ haben,
     * falls welche vorhanden, sonst eine leere Liste.
     *
     * @param rolleTyp
     * @return
     * @throws Exception
     */
    List<Benutzer> findByRolleTyp(RolleTyp rolleTyp) throws Exception;

    /**
     * Liefert den Benutzer zurück, dessen Nachname und Vorname übergeben
     * wurden.
     *
     * @param nachname
     * @param vorname
     * @return
     * @throws Exception
     */
    List<Benutzer> findByNachnameUndVorname(String nachname, String vorname) throws Exception;
}
