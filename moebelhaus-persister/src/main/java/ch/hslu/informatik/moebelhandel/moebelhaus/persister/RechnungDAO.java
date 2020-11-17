package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import java.util.GregorianCalendar;
import java.util.List;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Rechnung;

/**
 * Interface für Persistierung von Lager-Entities.
 * 
 * @version 1.0
 * @author jsucur
 * 
 */
public interface RechnungDAO extends GenericPersisterDAO<Rechnung> {

    /**
     * Liefert alle Rechnungen für das übergebene Datum zurück, falls welche
     * vorhanden, sonst eine leere Liste.
     *
     * @param datum
     * @return
     * @throws Exception
     */
    List<Rechnung> findByDatum(GregorianCalendar datum) throws Exception;

    /**
     * Liefert alle Rechnungen für den übergebene Benutzer zurück, falls welche
     * vorhanden, sonst eine leere Liste.
     *
     * @param benutzer
     * @return
     * @throws Exception
     */
    List<Rechnung> findByBenutzer(Benutzer benutzer) throws Exception;

    /**
     * Liefert alle Rechnungen für die übergebenen Benutzer und Datum zurück,
     * falls welche vorhanden, sonst eine leere Liste.
     *
     * @param person
     * @param datum
     * @return
     * @throws Exception
     */
    List<Rechnung> findByBenutzerUndDatum(Benutzer benutzer, GregorianCalendar datum) throws Exception;
}
