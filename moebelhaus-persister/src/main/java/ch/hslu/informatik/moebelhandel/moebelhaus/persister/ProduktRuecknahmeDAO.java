package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import java.util.GregorianCalendar;
import java.util.List;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktRuecknahme;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Rechnung;

/**
 * Interface für Persistierung von ProduktRuecknahme-Entities.
 * 
 * @version 1.0
 * @author jsucur
 * 
 */
public interface ProduktRuecknahmeDAO extends GenericPersisterDAO<ProduktRuecknahme> {

    /**
     * Liefert alle Produktrücknahmen für die übergebene Rechnung zurück.
     *
     * @param rechnung
     * @return
     * @throws Exception
     */
    List<ProduktRuecknahme> findByRechnung(Rechnung rechnung) throws Exception;

    /**
     * Liefert alle Produktrücknahmen für den übergebenen Produkt-Typ zurück,
     * falls welche vorhanden, sonst eine leere Liste.
     *
     * @param produktTyp
     * @return
     * @throws Exception
     */
    List<ProduktRuecknahme> findByProduktTyp(ProduktTyp produktTyp) throws Exception;

    /**
     * Liefert alle Produktrücknahmen für das übergebene Datum zurück, falls
     * welche vorhanden, sonst eine leere Liste.
     *
     * @param datum
     * @return
     * @throws Exception
     */
    List<ProduktRuecknahme> findByDatum(GregorianCalendar datum) throws Exception;
}
