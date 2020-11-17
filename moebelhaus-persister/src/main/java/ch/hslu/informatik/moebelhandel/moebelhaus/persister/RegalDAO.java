package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Regal;

/**
 * Interface für Persistierung von Regal-Entities.
 *
 * @version 1.0
 * @author jsucur
 *
 */
public interface RegalDAO extends GenericPersisterDAO<Regal> {

    /**
     * Liefert das Regal für die übergebene Bezeichnung zurück.
     *
     * @param bezeichnung
     * @return
     */
    Regal findByBezeichnung(String bezeichnung) throws Exception;

    /**
     * Liefert das Regal für die übergebene Nummer zurück.
     *
     * @param nummer
     * @return
     */
    Regal findByNummer(int nummer) throws Exception;

}
