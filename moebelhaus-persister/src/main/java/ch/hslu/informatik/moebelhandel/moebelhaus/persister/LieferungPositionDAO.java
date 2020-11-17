package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import java.util.List;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.LieferungPosition;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;

public interface LieferungPositionDAO extends GenericPersisterDAO<LieferungPosition> {

    /**
     * Liefert alle Lieferung-Positionen für den übergebenen Produkt-Typ zurück,
     * falls welche gefunden, sonst eine leere Liste.
     * 
     * @param produktTyp
     * @return
     * @throws Exception
     */
    List<LieferungPosition> findByProduktTyp(ProduktTyp produktTyp) throws Exception;
}
