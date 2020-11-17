package ch.hslu.informatik.moebelhandel.moebelhaus.api;

import java.util.List;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Bestellung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lieferant;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Moebelhaus;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Produkt;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Regal;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Ruecksendung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Tablar;

/**
 * Diese Schnittstelle gibt Funktionalitäten vor, welche dem Moebelhauslager zur
 * Verfügung stellen muss.
 *
 * @author jsucur
 */
public interface MoebelhausLagerService {

    /**
     * Bestellt weiteres Möbel bei dem Zentrallager.
     *
     * @param bestellung
     * @return
     * @throws Exception
     */
    Bestellung bestellen(Bestellung bestellung) throws Exception;

    /**
     * Liefert den Lagerbestand für den übergebenen Produkttyp zurück.
     *
     * @param produktTyp
     * @return
     * @throws Exception
     */
    List<Produkt> produktBestand(ProduktTyp produktTyp) throws Exception;

    /**
     * Liefert den Lagerbestand für die übergebenen Produkttyp und Typ-Code
     * zurück.
     *
     * @param produktTypCode
     * @return
     * @throws Exception
     */
    List<Produkt> produktBestand(String produktTypCode) throws Exception;

    /**
     * Diese Method ist für das Einlagern von Produkten zuständig. Bei der
     * Einlagerung wird jedem Produkt ein eindeutiger numerischer Code zugeteilt
     * (Produkt-Code).
     *
     * @param produktTyp
     * @param anzahl
     * @throws Exception
     */
    void produktEinlagern(ProduktTyp produktTyp, int anzahl) throws Exception;

    /**
     * Sendet ein Produkt ans Zentrallager zurück.
     *
     * @param produkt
     * @throws Exception
     */
    void produktRetournieren(Produkt produkt) throws Exception;

    /**
     * Entfernt die übergebene Produkte aus dem Lagerbestand, da sie verkauft
     * wurden.
     *
     * @param produktListe
     * @throws Exception
     */
    void produktAuslagern(List<Produkt> produktListe) throws Exception;

    /**
     * Fügt ein Regal ins Lager ein.
     *
     * @param nummer
     * @param anzahlTablare
     * @return
     * @throws Exception
     */
    Regal regalHinzufuegen(int nummer, int anzahlTablare) throws Exception;

    /**
     * Aktualisiert das übergebene Lager.
     *
     * @param regal
     * @return
     * @throws Exception
     */
    Regal regalAktualisieren(Regal regal) throws Exception;

    /**
     * Entfernt das übergebene Regal aus dem Lager.
     *
     * @param regal
     * @throws Exception
     */
    void regalLoeschen(Regal regal) throws Exception;

    /**
     * Liefert das Regal für die übergebene Bezeichnung zurück.
     *
     * @param bezeichnung
     * @return
     * @throws Exception
     */
    Regal regalByBezeichnung(String bezeichnung) throws Exception;

    /**
     * Liefert das Regal für die übergebene Nummer zurück.
     *
     * @param nummer
     * @return
     * @throws Exception
     */
    Regal regalByNummer(int nummer) throws Exception;

    /**
     * Liefert alle Regale zurück.
     *
     * @return
     * @throws Exception
     */
    List<Regal> alleRegale() throws Exception;

    /**
     * Liefert alle Lieferanten zurück.
     *
     * @return
     * @throws Exception
     */
    List<Lieferant> alleLieferanten() throws Exception;

    /**
     * Liefert das Regal, welches das übergebene Tablar enthält.
     *
     * @param tablar
     * @return
     * @throws Exception
     */
    Regal regalByTablar(Tablar tablar) throws Exception;

    /**
     * Liefert alle ProduktTypen zurück.
     *
     * @return
     */
    List<ProduktTyp> produktTypAlle() throws Exception;

    /**
     * Entfernt den übergebenen ProduktTyp aus dem Datenbestand.
     *
     * @param produktTyp
     */
    void produktTypLoeschen(ProduktTyp produktTyp) throws Exception;

    /**
     * Fügt einen neuen ProduktTyp in Datenbestand ein.
     *
     * @param produktTyp
     * @return
     * @throws Exception
     */
    ProduktTyp produktTypHinzufuegen(ProduktTyp produktTyp) throws Exception;

    /**
     * Aktualisiert den übergebenen ProduktTyp.
     *
     * @param produktTyp
     * @return
     * @throws Exception
     */
    ProduktTyp produktTypUpdaten(ProduktTyp produktTyp) throws Exception;

    /**
     * Fügt den übergebenen Lieferanten in den Datenbestand ein.
     *
     * @param lieferant
     * @return
     * @throws Exception
     */
    Lieferant lieferantenHinzufuegen(Lieferant lieferant) throws Exception;

    /**
     * Aktualisiert den übergebenen Lieferanten.
     *
     * @param lieferant
     * @return
     * @throws Exception
     */
    Lieferant lieferantenAktualisieren(Lieferant lieferant) throws Exception;

    /**
     * Entfernt den übergebenen Lieferanten aus dem Datenbestand.
     *
     * @param lieferant
     * @throws Exception
     */
    void lieferantenLoeschen(Lieferant lieferant) throws Exception;

    /**
     * Lieferat alle Produkttypen des übergebeneen Lieferanten zurück.
     *
     * @param lieferant
     * @return
     * @throws Exception
     */
    List<ProduktTyp> findeByLieferant(Lieferant lieferant) throws Exception;

    /**
     * Lieferat alle ProdktTyp-Objekte für den übergebenen Namen zurück.
     *
     * @param name
     * @return
     * @throws Exception
     */
    List<ProduktTyp> findeByProduktname(String name) throws Exception;

    /**
     * Liefert den ProduktTyp für den übergebenen ProduktTypCode zurück.
     *
     * @param produktTypCode
     * @return
     * @throws Exception
     */
    ProduktTyp findeByProduktTypCode(String produktTypCode) throws Exception;

    /**
     * Liefert das Moebelhaus zurück.
     *
     * @return
     * @throws Exception
     */
    Moebelhaus getMoebelhaus() throws Exception;

    /**
     * Liefert den ProduktCode zurück.
     *
     * @return
     * @throws Exception
     */
    long getProduktCode() throws Exception;

    /**
     * Liefert alle Bestellungen zurück.
     *
     * @return
     * @throws Exception
     */
    List<Bestellung> alleBestellungen() throws Exception;

    /**
     * Liefert die Bestellung für die übergebene Id zurück.
     *
     * @param id
     * @return
     * @throws Exception
     */
    Bestellung findeBestellungById(long id) throws Exception;

    /**
     * Fügt die übergebene Ruecksendung in den Datenbestand ein.
     *
     * @param ruecksendung
     * @throws Exception
     */
    void ruecksendungHinzufuegen(Ruecksendung ruecksendung) throws Exception;
}
