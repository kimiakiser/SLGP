package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import java.util.List;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lieferant;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;

/**
 * Interface für Persistierung von ProduktTyp-Entities.
 * 
 * @version 1.0
 * @author jsucur
 * 
 */
public interface ProduktTypDAO extends GenericPersisterDAO<ProduktTyp> {

    /**
     * Liefert alle ProduktTypen für den übergebenen Namen zurück.
     * 
     * @param name
     * @return
     * @throws Exception
     */
    List<ProduktTyp> findByName(String name) throws Exception;

    /**
     * Liefert den ProduktTyp für den übergebenen Typ-Code zurück.
     * 
     * @param typCode
     * @return
     * @throws Exception
     */
    ProduktTyp findByTypCode(String typCode) throws Exception;

    /**
     * Lieferat alle ProduktTypen zurück, die von dem übergebenen Lieferanten
     * gelieferat werden, falls welche gefunden, sonst eine leere Liste.
     * 
     * @param lieferant
     * @return
     * @throws Exception
     */
    List<ProduktTyp> findByLieferant(Lieferant lieferant) throws Exception;
}
