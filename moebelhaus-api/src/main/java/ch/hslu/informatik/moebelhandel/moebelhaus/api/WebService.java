package ch.hslu.informatik.moebelhandel.moebelhaus.api;

import java.util.List;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Bestellung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lieferung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Moebelhaus;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;

/**
 * Diese Schnittstelle gibt Funktionalitäten vor, welche den Webservices zur
 * Verfügung gestellt werden.
 *
 * @author asfedere
 */

public interface WebService {
	
    /**
     * Ist nicht implementiert.
     *
     * @param 
     * @return List ProduktTyp
     * @throws Exception
     */
	List<ProduktTyp> produktKatalog() throws Exception;
	
    /**
     * Liefert den Lagerbestand der Möbelhersteller zurück.
     *
     * @param ProduktTyp
     * @return Integer
     * @throws Exception
     */
    int produktBestand(ProduktTyp produktTyp) throws Exception;
    
    /**
     * Bestellt Möbel beim entsprechenden Möbelhersteller via Webservice.
     *
     * @param Bestellung
     * @return
     * @throws Exception
     */
    void bestellen(Bestellung bestellung, String moebelhausCode) throws Exception;

    /**
     * Liefert alle offenen Bestelleungen aller Webservices.
     *
     * @param Moebelhaus
     * @return List Bestellung
     * @throws Exception
     */
    List<Bestellung> offeneBestellungen(Moebelhaus moebelhaus) throws Exception;
    
    /**
     * Liefert die Lieferung für die übergebene Nummer vom Webservice.
     *
     * @param Long
     * @return Bestellung
     * @throws Exception
     */
    Bestellung lieferungByNummer(long lieferungNummer) throws Exception;
    
    /**
     * Ist nicht implementiert.
     *
     * @param Long
     * @return List Lieferung
     * @throws Exception
     */
    List<Lieferung> lieferungenByMoebelhaus(String moebelhausCode) throws Exception;
}