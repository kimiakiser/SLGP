package ch.hslu.informatik.moebelhandel.moebelhaus.api;

import java.util.List;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Bestellung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktRuecknahme;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Rechnung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Ruecksendung;

/**
 * Diese Schnittstelle schreibt die Funktionalitäten vor, welche für
 * PDF-Ausdruck benötigt werden.
 *
 * @author jsucur
 *
 */
public interface PrinterService {

    /**
     * Erstellt die Rechnung als PDF.
     *
     * @param rechnung
     */
    void printRechnungAlsPdf(Rechnung rechnung) throws Exception;

    /**
     * Erstellt die Rücknahme-Bestätigung als PDF
     *
     * @param produktRuecknahme
     */
    void printRuecknahmeAlsPdf(ProduktRuecknahme produktRuecknahme) throws Exception;

    /**
     * Erstellt den Tagesabschluss als PDF
     *
     * @param rechnungListe
     * @throws Exception
     */
    void printAbschlussAlsPdf(List<Rechnung> rechnungListe, Benutzer benutzer) throws Exception;

    /**
     * Erstellt die Info-Karte für ein Produkttyp.
     *
     * @param produktTyp
     * @throws Exception
     */
    void printInfoKarte(ProduktTyp produktTyp) throws Exception;

    /**
     * Erstellt die Bestellung als PDF.
     *
     * @param bestellung
     * @throws Exception
     */
    void printBestellungAlsPdf(Bestellung bestellung) throws Exception;

    /**
     * Erstellt die Rücksendung als PDF.
     *
     * @param ruecksendung
     */
    void printRuecksendungAlsPdf(Ruecksendung ruecksendung) throws Exception;
}
