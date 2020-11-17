package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import java.util.GregorianCalendar;
import java.util.List;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Ruecksendung;

public interface RuecksendungDAO extends GenericPersisterDAO<Ruecksendung> {

    /**
     * Liefert alle Rücksendungen für das übergebene Datum zurück, falls welche
     * vorhanden, sonst eine leere Liste.
     *
     * @param datum
     * @return
     * @throws Exception
     */
    List<Ruecksendung> findByDatum(GregorianCalendar datum) throws Exception;
}
