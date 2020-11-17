package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import java.util.List;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Produkt;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;

/**
 * Interface für Persistierung von Produkt-Entities.
 *
 * @version 1.0
 * @author jsucur
 *
 */
public interface ProduktDAO extends GenericPersisterDAO<Produkt> {

    /**
     * Liefert alle Produkte für das übergebene ProduktTyp zurück, falls welche
     * vorhanden, sonst eine leere Liste.
     *
     * @param produktTyp
     * @return
     * @throws Exception
     */
    List<Produkt> findByProduktTyp(ProduktTyp produktTyp) throws Exception;

    /**
     * Liefert alle Produkte für den übergebenen ProduktTyp-Code zurück, falls
     * welche vorhanden, sonst eine leere Liste.
     *
     * @param typCode
     * @return
     * @throws Exception
     */
    List<Produkt> findByProduktTypCode(String typCode) throws Exception;

    /**
     * Liefert das Produkt für den übergebenen Code zurück.
     *
     * @param code
     * @return
     * @throws Exception
     */
    Produkt findByCode(long code) throws Exception;
}
