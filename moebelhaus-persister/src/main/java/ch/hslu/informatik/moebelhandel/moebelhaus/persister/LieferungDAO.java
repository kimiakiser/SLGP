package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import java.util.GregorianCalendar;
import java.util.List;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lieferung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Moebelhaus;

/**
 * Interface für Persistierung von Lieferung-Entities.
 * 
 * @version 1.0
 * @author jsucur
 * 
 */
public interface LieferungDAO extends GenericPersisterDAO<Lieferung> {

    /**
     * Liefert alle Lieferungen für das übergebene Datum zurück, falls welche
     * vorhanden, sonst eine leere Liste.
     * 
     * @param datum
     * @return
     * @throws Exception
     */
    List<Lieferung> findByDatum(GregorianCalendar datum) throws Exception;

    /**
     * Liefert alle Liererungen für die übergebene Filiale zurück, falls welche
     * vorhanden, sonst eine leere Liste.
     * 
     * @param filiale
     * @return
     * @throws Exception
     */
    List<Lieferung> findByFiliale(Moebelhaus filiale) throws Exception;

}
