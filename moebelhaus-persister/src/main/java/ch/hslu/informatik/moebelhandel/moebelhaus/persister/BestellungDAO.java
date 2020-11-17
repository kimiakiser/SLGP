package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import java.util.GregorianCalendar;
import java.util.List;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Bestellung;

/**
 * Interface für Persistierung von Bestellung-Entities.
 * 
 * @version 1.0
 * @author jsucur
 * 
 */
public interface BestellungDAO extends GenericPersisterDAO<Bestellung> {

    /**
     * Liefert alle Bestellungen zurück, welche die Filiale an dem übergebeneen
     * Datum getätigt hat, falls welche gefunden, sonst eine leere liste.
     * 
     * @param datum
     * @return
     * @throws Exception
     */
    List<Bestellung> findByDatum(GregorianCalendar datum) throws Exception;
}
