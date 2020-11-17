package ch.hslu.informatik.moebelhandel.moebelhaus.api;

import java.util.GregorianCalendar;
import java.util.List;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Produkt;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktRuecknahme;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Rechnung;

/**
 * Diese Schnittstelle schreibt die Funktionalitäten vor, welche von der
 * KasseService benötigt werden.
 *
 * @author jsucur
 *
 */
public interface KasseService {

	/**
	 * Realisiert das Verkaufen aller Produkte in der Liste.
	 *
	 * @param produktListe
	 * @param benutzer
	 * @throws Exception
	 */
	Rechnung verkaufen(List<Produkt> produktListe, Benutzer benutzer) throws Exception;

	/**
	 * Realisiert die Zurücknahme eines Produktes.
	 *
	 * @param produktRuecknahme
	 * @throws Exception
	 */
	void produktZuruecknehmen(ProduktRuecknahme produktRuecknahme) throws Exception;

	/**
	 * Liefert die Rechnung für die übergebene Rechnungsnummer zurück;
	 *
	 * @param nummer
	 * @return
	 * @throws Exception
	 */
	Rechnung findByRechnungNummer(long nummer) throws Exception;

	/**
	 * Liefert den ProduktTyp für den übergebenen ProduktTyp Code zurück.
	 *
	 * @param produktTypCode
	 * @return
	 * @throws Exception
	 */
	ProduktTyp findByProduktTypCode(String produktTypCode) throws Exception;

	/**
	 * Liefert das Produkt für den übergebenen Produkt-Code zurück.
	 *
	 * @param produktCode
	 * @return
	 * @throws Exception
	 */
	Produkt findByProduktCode(long produktCode) throws Exception;

	/**
	 * Liefert alle Rechnung für die übergebenen Benutezr und Datum zurück,
	 * falls welche Vorhanden, sonst eine leere Liste.
	 * 
	 * @param benutzer
	 * @param datum
	 * @return
	 * @throws Exception
	 */
	List<Rechnung> findByBenutzerUndDatum(Benutzer benutzer, GregorianCalendar datum) throws Exception;

	/**
	 * Liefert alle am Lager verfügbaren Produkte zurück, falls welche
	 * vorhanden, sonst eine leere Liste.
	 * 
	 * @return
	 * @throws Exception
	 */
	List<Produkt> findAlleAmLagerVerfuegbareProdukte() throws Exception;
}
