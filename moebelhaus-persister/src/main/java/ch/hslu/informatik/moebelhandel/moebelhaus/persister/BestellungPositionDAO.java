package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import java.util.List;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.BestellungPosition;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;

public interface BestellungPositionDAO extends GenericPersisterDAO<BestellungPosition> {

    /**
     * Lieferat alle BestellungPosition-Objekte für den übergebenen ProduktTyp
     * zurück, falls welche vorhanden, sonst eine leere Liste.
     * 
     * @param produktTyp
     * @return
     * @throws Exception
     */
    List<BestellungPosition> findByProduktTyp(ProduktTyp produktTyp) throws Exception;
}
