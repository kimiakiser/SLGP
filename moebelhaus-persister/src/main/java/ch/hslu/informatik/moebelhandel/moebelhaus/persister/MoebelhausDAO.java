package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Moebelhaus;

/**
 * Interface für Persistierung von Filiale-Entities.
 * 
 * @version 1.0
 * @author jsucur
 * 
 */
public interface MoebelhausDAO extends GenericPersisterDAO<Moebelhaus> {
    /**
     * Liefert das Möbelhaus für den übergebenen Namen zurück.
     * 
     * @param name
     * @return
     * @throws Exception
     */
    Moebelhaus findByName(String name) throws Exception;

    /**
     * Liefert das Möbelhaus für den übergebenen Code zurück.
     * 
     * @param moebelhausCode
     * @return
     * @throws Exception
     */
    Moebelhaus findByMoebelhausCode(String moebelhausCode) throws Exception;
}
