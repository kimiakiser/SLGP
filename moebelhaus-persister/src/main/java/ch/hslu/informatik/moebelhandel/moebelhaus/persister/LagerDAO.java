package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lager;

/**
 * Interface für Persistierung von Lager-Entities.
 * 
 * @version 1.0
 * @author jsucur
 * 
 */
public interface LagerDAO extends GenericPersisterDAO<Lager> {

    /**
     * Liefert das Lager für den übergebenen Namen zurück.
     * 
     * @param name
     * @return
     * @throws Exception
     */
    Lager findByName(String name) throws Exception;
}
