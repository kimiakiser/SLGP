package ch.hslu.informatik.moebelhandel.moebelhaus.api;

import java.util.List;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.RolleTyp;

/**
 * Diese Schnittstelle gibt Funktionalitäten vor, welche für die Verwaltung von
 * Benutzer zur Verfügung gestellt werden müssen.
 *
 * @author jsucur
 */
public interface BenutzerService {

    /**
     * Fügt den übergebenen Benutzer in den Datenbestand ein.
     *
     * @param benutzer
     * @return
     * @throws Exception
     */
    Benutzer benutzerHinzufuegen(Benutzer benutzer) throws Exception;

    /**
     * Aktualisiert den übergebenen Benutzer.
     *
     * @param benutzer
     * @return
     * @throws Exception
     */
    Benutzer benutzerAktualisieren(Benutzer benutzer) throws Exception;

    /**
     * Entfernt den übergebenen Benutzer aus dem Datenbestand.
     *
     * @param benutzer
     * @throws Exception
     */
    void benutzerLoeschen(Benutzer benutzer) throws Exception;

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

    /**
     * Liefert alle Benutzer zurück.
     *
     * @return
     * @throws Exception
     */
    List<Benutzer> alleBenutzer() throws Exception;

    /**
     * Liefert alle Rollen zurück.
     *
     * @return
     * @throws Exception
     */
    List<RolleTyp> alleRollen() throws Exception;
}
