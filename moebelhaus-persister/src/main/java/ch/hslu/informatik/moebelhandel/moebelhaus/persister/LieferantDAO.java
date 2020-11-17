package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lieferant;

/**
 * Interface für Persistierung von Lieferant-Entities.
 * 
 * @version 1.0
 * @author jsucur
 * 
 */
public interface LieferantDAO extends GenericPersisterDAO<Lieferant> {

    /**
     * Liefert den Lieferanten für den übergebenen Namen zurück.
     * 
     * @param name
     * @return
     * @throws Exception
     */
    Lieferant findByName(String name) throws Exception;
}
