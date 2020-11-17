package ch.hslu.informatik.moebelhandel.moebelhaus.api;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;

/**
 * Diese Schnittstelle schreibt die Funktionalitäten vor, welche für Anmeldung
 * und Kennwortänderung benötigt werden.
 * 
 * @author jsucur
 */
public interface LoginService {

    /**
     * Liefert die Id des Benutzers für die eingegebenen Benutzernamen und
     * Kennwort zurück.
     *
     * @param benutzername
     * @param kennwort
     * @return
     * @throws Exception
     */
    Benutzer login(String benutzername, String kennwort) throws Exception;

    /**
     * Diese Methode ermöglicht die Änderung des aktuellen Kennworts.
     *
     * @param benutzername
     * @param kennwortAktuell
     * @param kennwortNeu
     * @return
     * @throws Exception
     */
    boolean kennwortAendern(String benutzername, String kennwortAktuell, String kennwortNeu) throws Exception;
}
