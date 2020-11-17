package ch.hslu.informatik.moebelhandel.moebelhaus.pdfprinter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ch.hslu.informatik.moebelhandel.moebelhaus.api.PrinterService;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Bestellung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.BestellungPosition;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Moebelhaus;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktRuecknahme;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Rechnung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.RechnungPosition;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Ruecksendung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.RuecksendungPosition;

public class PdfPrinter implements PrinterService {

	private static Logger logger = LogManager.getLogger(PdfPrinter.class);

	/**
	 * Erstellt die Rechnung als PDF.
	 *
	 * @param rechnung
	 */

	public void printRechnungAlsPdf(Rechnung rechnung) throws Exception {

		SimpleDateFormat sdfName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
		FileOutputStream fos = null;

		try {

			int cnt = 1;

			Properties props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("printer.properties"));
			String outputDir = ausgabeVerzeichnis(props);

			String defaultFileName = props.getProperty("rechnung_default_file_name");

			Document pdfDocument = new Document();
			fos = new FileOutputStream(
					outputDir + File.separator + sdfName.format(new Date()) + "-" + defaultFileName + ".pdf");
			PdfWriter.getInstance(pdfDocument, fos);
			pdfDocument.open();

			/* Fonts */
			Font anschriftFont = FontFactory.getFont("Courier", 8, BaseColor.GRAY);
			Font titelFont = FontFactory.getFont("Courier", 14, BaseColor.GRAY);
			titelFont.setStyle(Font.BOLD);
			Font zwischenzeileFont = FontFactory.getFont("Courier", 6, BaseColor.GRAY);
			Font tableFont = FontFactory.getFont("Courier", 10, BaseColor.GRAY);
			Font descriptionFont = FontFactory.getFont("Courier", 8, BaseColor.GRAY);

			/* Anschrift zusammenstellen */
			StringBuilder sb = new StringBuilder();

			sb.append(props.getProperty("anschrift_zeile_1")).append("\n");
			sb.append(props.getProperty("anschrift_zeile_2")).append("\n");
			sb.append(props.getProperty("anschrift_zeile_3")).append("\n");
			sb.append(props.getProperty("anschrift_zeile_4")).append("\n\n\n\n\n");

			Paragraph paragraph = new Paragraph(sb.toString(), anschriftFont);
			paragraph.setAlignment(Element.ALIGN_RIGHT);
			pdfDocument.add(paragraph);

			/* Tabelle */
			PdfPTable tbl = new PdfPTable(5);

			/* Spaltenbreite definieren */
			float[] columnWidths = new float[] { 8f, 52f, 15f, 10f, 15f };
			tbl.setWidths(columnWidths);

			PdfPCell cell = new PdfPCell();

			/* Rechnung-Nr. */
			cell = new PdfPCell(new Phrase("RECHNUNG NR. " + rechnung.getId(), titelFont));
			cell.setColspan(5);
			cell.setBorderWidth(0);
			tbl.addCell(cell);

			/* Zwischenzeile */
			cell = new PdfPCell(new Phrase(" ", zwischenzeileFont));
			cell.setColspan(5);
			cell.setBorderWidth(0);
			tbl.addCell(cell);

			/* Überschriften */
			cell = new PdfPCell(new Phrase("Nr.", tableFont));
			cell.setBorderColor(BaseColor.GRAY);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase("Beschreibung", tableFont));
			cell.setBorderColor(BaseColor.GRAY);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase("Preis [CHF]", tableFont));
			cell.setBorderColor(BaseColor.GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase("Anzahl", tableFont));
			cell.setBorderColor(BaseColor.GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase("Betrag [CHF]", tableFont));
			cell.setBorderColor(BaseColor.GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl.addCell(cell);

			for (RechnungPosition pos : rechnung.getRechnungPositionListe()) {

				cell = new PdfPCell(new Phrase("" + cnt++, tableFont));
				cell.setBorderColor(BaseColor.GRAY);
				tbl.addCell(cell);

				cell = new PdfPCell(new Phrase(pos.getProduktCodeUndBeschreibung(), descriptionFont));
				cell.setBorderColor(BaseColor.GRAY);
				tbl.addCell(cell);

				cell = new PdfPCell(new Phrase("" + pos.getProduktTyp().getPreis(), tableFont));
				cell.setBorderColor(BaseColor.GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tbl.addCell(cell);

				cell = new PdfPCell(new Phrase("" + pos.getAnzahl(), tableFont));
				cell.setBorderColor(BaseColor.GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tbl.addCell(cell);

				cell = new PdfPCell(new Phrase("" + pos.getBetrag(), tableFont));
				cell.setBorderColor(BaseColor.GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tbl.addCell(cell);
			}

			cell = new PdfPCell(new Phrase("Totalbetrag", tableFont));
			cell.setBorderColor(BaseColor.GRAY);
			cell.setColspan(4);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase("" + rechnung.getBetrag(), tableFont));
			cell.setBorderColor(BaseColor.GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl.addCell(cell);

			pdfDocument.add(tbl);

			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy ' um ' HH:mm:ss");
			String zeitpunkt = sdf.format(new Date());

			Font datumFont = FontFactory.getFont("Courier", 8, BaseColor.GRAY);

			paragraph = new Paragraph("\n\nDatum / Zeit: " + zeitpunkt, datumFont);
			paragraph.setAlignment(Element.ALIGN_RIGHT);

			pdfDocument.add(paragraph);

			pdfDocument.close();

		} catch (Exception e) {
			logger.error("Fehler bei der Generierung der Rechnung als PDF: ", e);
			throw new RuntimeException(e);
		} finally {
			if (fos != null) {
				fos.close();
			}
		}

	}

	/**
	 * Erstellt die Rücknahme-Bestätigung als PDF
	 *
	 * @param produktRuecknahme
	 */
	public void printRuecknahmeAlsPdf(ProduktRuecknahme produktRuecknahme) throws Exception {

		SimpleDateFormat sdfName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy ' um ' HH:mm:ss");

		FileOutputStream fos = null;

		try {

			int cnt = 1;

			Properties props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("printer.properties"));
			String outputDir = ausgabeVerzeichnis(props);
			String defaultFileName = props.getProperty("ruecknahme_default_file_name");

			Document pdfDocument = new Document();
			fos = new FileOutputStream(
					outputDir + File.separator + sdfName.format(new Date()) + "-" + defaultFileName + ".pdf");
			PdfWriter.getInstance(pdfDocument, fos);

			pdfDocument.open();

			/* Fonts */
			Font anschriftFont = FontFactory.getFont("Courier", 8, BaseColor.GRAY);
			Font titelFont = FontFactory.getFont("Courier", 14, BaseColor.GRAY);
			titelFont.setStyle(Font.BOLD);
			Font zwischenzeileFont = FontFactory.getFont("Courier", 6, BaseColor.GRAY);
			Font tableFont = FontFactory.getFont("Courier", 10, BaseColor.GRAY);
			Font descriptionFont = FontFactory.getFont("Courier", 8, BaseColor.GRAY);

			/* Anschrift zusammenstelle */
			StringBuilder sb = new StringBuilder();

			sb.append(props.getProperty("anschrift_zeile_1")).append("\n");
			sb.append(props.getProperty("anschrift_zeile_2")).append("\n");
			sb.append(props.getProperty("anschrift_zeile_3")).append("\n");
			sb.append(props.getProperty("anschrift_zeile_4")).append("\n\n\n\n\n");

			Paragraph paragraph = new Paragraph(sb.toString(), anschriftFont);
			paragraph.setAlignment(Element.ALIGN_RIGHT);
			pdfDocument.add(paragraph);

			/* Tabelle */
			PdfPTable tbl = new PdfPTable(5);

			/* Spaltenbreite definieren */
			float[] columnWidths = new float[] { 8f, 52f, 15f, 10f, 15f };
			tbl.setWidths(columnWidths);

			PdfPCell cell = new PdfPCell();

			/* Rechnung-Nr. */
			cell = new PdfPCell(new Phrase("PRODUKTRÜCKNAHME", titelFont));
			cell.setColspan(5);
			cell.setBorderWidth(0);
			tbl.addCell(cell);

			/* Zwischenzeile */
			cell = new PdfPCell(new Phrase(" ", zwischenzeileFont));
			cell.setColspan(5);
			cell.setBorderWidth(0);
			tbl.addCell(cell);

			/* Überschriften */
			cell = new PdfPCell(new Phrase("Nr.", tableFont));
			cell.setBorderColor(BaseColor.GRAY);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase("Beschreibung", tableFont));
			cell.setBorderColor(BaseColor.GRAY);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase("Preis [CHF]", tableFont));
			cell.setBorderColor(BaseColor.GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase("Anzahl", tableFont));
			cell.setBorderColor(BaseColor.GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase("Betrag [CHF]", tableFont));
			cell.setBorderColor(BaseColor.GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase("" + cnt++, tableFont));
			cell.setBorderColor(BaseColor.GRAY);
			tbl.addCell(cell);

			cell = new PdfPCell(
					new Phrase(produktRuecknahme.getProduktTyp().getProduktCodeUndBeschreibung(), descriptionFont));
			cell.setBorderColor(BaseColor.GRAY);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase("" + produktRuecknahme.getProduktTyp().getPreis(), tableFont));
			cell.setBorderColor(BaseColor.GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase("" + produktRuecknahme.getAnzahl(), tableFont));
			cell.setBorderColor(BaseColor.GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase("" + produktRuecknahme.getBetrag(), tableFont));
			cell.setBorderColor(BaseColor.GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase("Totalbetrag", tableFont));
			cell.setBorderColor(BaseColor.GRAY);
			cell.setColspan(4);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase("" + produktRuecknahme.getBetrag(), tableFont));
			cell.setBorderColor(BaseColor.GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl.addCell(cell);

			/* Angaben zur Rechnung */
			Date datumVerkauf = new Date(produktRuecknahme.getRechnung().getZeit().getTimeInMillis());
			cell = new PdfPCell(new Phrase("Rechnung-Nr.: " + produktRuecknahme.getRechnung().getId()
					+ ", Datum / Zeit: " + sdf.format(datumVerkauf), tableFont));

			cell.setBorderColor(BaseColor.GRAY);
			cell.setColspan(5);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbl.addCell(cell);

			/* Zwischenzeile */
			cell = new PdfPCell(new Phrase(" ", zwischenzeileFont));
			cell.setColspan(5);
			cell.setBorderWidth(0);
			tbl.addCell(cell);

			/* Grund für Rücknahme */
			String grund = "Grund:\n" + produktRuecknahme.getBemerkung();
			cell = new PdfPCell(new Phrase(grund, tableFont));

			cell.setBorderColor(BaseColor.GRAY);
			cell.setColspan(5);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbl.addCell(cell);

			pdfDocument.add(tbl);

			String zeitpunkt = sdf.format(new Date());

			Font datumFont = FontFactory.getFont("Courier", 8, BaseColor.GRAY);

			/* Schlussangaben */
			paragraph = new Paragraph("\n\nDatum / Zeit: " + zeitpunkt, datumFont);
			paragraph.setAlignment(Element.ALIGN_RIGHT);

			pdfDocument.add(paragraph);

			pdfDocument.close();

		} catch (Exception e) {
			logger.error("Fehler bei der Generierung der Rücknahme als PDF: ", e);
			throw new RuntimeException(e);
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}

	/**
	 * Erstellt den Tagesabschluss als PDF
	 *
	 * @param rechnungListe
	 * @throws Exception
	 */
	public void printAbschlussAlsPdf(List<Rechnung> rechnungListe, Benutzer benutzer) throws Exception {

		FileOutputStream fos = null;
		if (rechnungListe.isEmpty()) {
			return;
		}

		Properties props = new Properties();
		props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("printer.properties"));
		String outputDir = ausgabeVerzeichnis(props);
		String defaultFileName = props.getProperty("tagesabschluss_default_file_name");

		SimpleDateFormat sdfDatum = new SimpleDateFormat("yyyy-MM-dd");

		GregorianCalendar datum = rechnungListe.get(0).getZeit();

		String datumAlsString = sdfDatum.format(new Date(datum.getTimeInMillis()));

		try {

			fos = new FileOutputStream(outputDir + File.separator + sdfDatum.format(new Date()) + "-" + defaultFileName
					+ "-" + benutzer.getCredentials().getBenutzername() + ".pdf");

			int cnt = 1;

			Document pdfDocument = new Document();
			PdfWriter.getInstance(pdfDocument, fos);
			pdfDocument.open();

			/* Fonts */
			Font anschriftFont = FontFactory.getFont("Courier", 8, BaseColor.GRAY);
			Font titelFont = FontFactory.getFont("Courier", 14, BaseColor.GRAY);
			titelFont.setStyle(Font.BOLD);
			Font zwischenzeileFont = FontFactory.getFont("Courier", 6, BaseColor.GRAY);
			Font tableFont = FontFactory.getFont("Courier", 10, BaseColor.GRAY);
			Font descriptionFont = FontFactory.getFont("Courier", 8, BaseColor.GRAY);

			/* Anschrift zusammenstellen */
			StringBuilder sb = new StringBuilder();

			sb.append(props.getProperty("anschrift_zeile_1")).append("\n");
			sb.append(props.getProperty("anschrift_zeile_2")).append("\n");
			sb.append(props.getProperty("anschrift_zeile_3")).append("\n");
			sb.append(props.getProperty("anschrift_zeile_4")).append("\n\n\n\n\n");

			Paragraph paragraph = new Paragraph(sb.toString(), anschriftFont);
			paragraph.setAlignment(Element.ALIGN_RIGHT);
			pdfDocument.add(paragraph);

			/* Tabelle */
			PdfPTable tbl = new PdfPTable(3);

			/* Spaltenbreite definieren */
			float[] columnWidths = new float[] { 10f, 60f, 30f };
			tbl.setWidths(columnWidths);

			PdfPCell cell = new PdfPCell();

			/* Tagesabschluss */
			cell = new PdfPCell(new Phrase("Tagesabschluss - " + benutzer.getNachname() + " " + benutzer.getVorname()
					+ " am " + datumAlsString, titelFont));
			cell.setColspan(3);
			cell.setBorderWidth(0);
			tbl.addCell(cell);

			/* Zwischenzeile */
			cell = new PdfPCell(new Phrase(" ", zwischenzeileFont));
			cell.setColspan(3);
			cell.setBorderWidth(0);
			tbl.addCell(cell);

			/* Überschriften */
			cell = new PdfPCell(new Phrase("Nr.", tableFont));
			cell.setBorderColor(BaseColor.GRAY);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase("Rechnung-Nr.", tableFont));
			cell.setBorderColor(BaseColor.GRAY);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase("Betrag [CHF]", tableFont));
			cell.setBorderColor(BaseColor.GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl.addCell(cell);

			for (Rechnung pos : rechnungListe) {

				cell = new PdfPCell(new Phrase("" + cnt++, tableFont));
				cell.setBorderColor(BaseColor.GRAY);
				tbl.addCell(cell);

				cell = new PdfPCell(new Phrase("" + pos.getId(), descriptionFont));
				cell.setBorderColor(BaseColor.GRAY);
				tbl.addCell(cell);

				cell = new PdfPCell(new Phrase("" + pos.getBetrag(), tableFont));
				cell.setBorderColor(BaseColor.GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tbl.addCell(cell);
			}

			cell = new PdfPCell(new Phrase("Totalbetrag", tableFont));
			cell.setBorderColor(BaseColor.GRAY);
			cell.setColspan(2);
			tbl.addCell(cell);

			double betrag = 0;

			for (Rechnung r : rechnungListe) {
				betrag += r.getBetrag();
			}

			cell = new PdfPCell(new Phrase("" + betrag, tableFont));
			cell.setBorderColor(BaseColor.GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl.addCell(cell);

			pdfDocument.add(tbl);

			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy ' um ' HH:mm:ss");
			String zeitpunkt = sdf.format(new Date());

			Font datumFont = FontFactory.getFont("Courier", 8, BaseColor.GRAY);

			paragraph = new Paragraph("\n\nDatum / Zeit: " + zeitpunkt, datumFont);
			paragraph.setAlignment(Element.ALIGN_RIGHT);

			pdfDocument.add(paragraph);

			pdfDocument.close();

		} catch (Exception e) {
			logger.error("Fehler bei der Generierung des Tagesabschlusses als PDF: ", e);
			throw new RuntimeException(e);
		} finally {
			if (fos != null) {
				fos.close();
			}
		}

	}

	/**
	 * Erstellt die Info-Karte für ein Produkttyp.
	 *
	 * @param produktTyp
	 * @throws Exception
	 */
	public void printInfoKarte(ProduktTyp produktTyp) throws Exception {

		FileOutputStream fos = null;

		if (produktTyp == null) {
			return;
		}

		Properties props = new Properties();
		props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("printer.properties"));
		String outputDir = ausgabeVerzeichnis(props);
		String defaultFileName = props.getProperty("infokarte_default_file_name") + "_" + produktTyp.getTypCode();

		SimpleDateFormat sdfDatum = new SimpleDateFormat("yyyy-MM-dd");

		try {

			Document pdfDocument = new Document();
			fos = new FileOutputStream(
					outputDir + File.separator + sdfDatum.format(new Date()) + "-" + defaultFileName + ".pdf");
			PdfWriter.getInstance(pdfDocument, fos);
			pdfDocument.open();

			/* Fonts definiren */
			Font anschriftFont = FontFactory.getFont("Courier", 8, BaseColor.GRAY);

			Font titelFont = FontFactory.getFont("Courier", 14, BaseColor.GRAY);
			titelFont.setStyle(Font.BOLD);

			Font zwischenzeileFont = FontFactory.getFont("Courier", 6, BaseColor.GRAY);

			Font headingFont = FontFactory.getFont("Courier", 10, BaseColor.GRAY);
			headingFont.setStyle(Font.BOLD);
			Font contentFont = FontFactory.getFont("Courier", 10, BaseColor.GRAY);
			Font descriptionFont = FontFactory.getFont("Courier", 8, BaseColor.GRAY);

			/* Anschrift zusammensetzen */
			StringBuilder sb = new StringBuilder();

			sb.append(props.getProperty("anschrift_zeile_1")).append("\n");
			sb.append(props.getProperty("anschrift_zeile_2")).append("\n");
			sb.append(props.getProperty("anschrift_zeile_3")).append("\n");
			sb.append(props.getProperty("anschrift_zeile_4")).append("\n\n\n\n\n");

			Paragraph paragraph = new Paragraph(sb.toString(), anschriftFont);
			paragraph.setAlignment(Element.ALIGN_RIGHT);
			pdfDocument.add(paragraph);

			/* Tabelle */
			PdfPTable tbl = new PdfPTable(2);

			/* Spaltenbreite definieren */
			float[] columnWidths = new float[] { 20f, 80f };
			tbl.setWidths(columnWidths);

			PdfPCell cell = new PdfPCell();

			/* Infokarte */
			cell = new PdfPCell(new Phrase("Infokarte", titelFont));
			cell.setColspan(2);
			cell.setBorderWidth(0);
			tbl.addCell(cell);

			/* Zwischenzeile */
			cell = new PdfPCell(new Phrase(" ", zwischenzeileFont));
			cell.setColspan(2);
			cell.setBorderWidth(0);
			tbl.addCell(cell);

			/* ProduktTyp-Name */
			cell = new PdfPCell(new Phrase("Produkt-Name", headingFont));
			cell.setBorderColor(BaseColor.GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase(produktTyp.getName(), contentFont));
			cell.setBorderColor(BaseColor.GRAY);
			tbl.addCell(cell);

			/* ProduktTyp-Code */
			cell = new PdfPCell(new Phrase("Produkt-Code", headingFont));
			cell.setBorderColor(BaseColor.GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase(produktTyp.getTypCode(), contentFont));
			cell.setBorderColor(BaseColor.GRAY);
			tbl.addCell(cell);

			/* Beschreibung */
			cell = new PdfPCell(new Phrase("Beschreibung", headingFont));
			cell.setBorderColor(BaseColor.GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase(produktTyp.getBeschreibung(), descriptionFont));
			cell.setBorderColor(BaseColor.GRAY);
			tbl.addCell(cell);

			/* Preis */
			cell = new PdfPCell(new Phrase("Preis", headingFont));
			cell.setBorderColor(BaseColor.GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase("" + produktTyp.getPreis() + " CHF", contentFont));
			cell.setBorderColor(BaseColor.GRAY);
			tbl.addCell(cell);

			/* Lieferant */
			cell = new PdfPCell(new Phrase("Lieferant", headingFont));
			cell.setBorderColor(BaseColor.GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase(
					produktTyp.getLieferant() + ", " + produktTyp.getLieferant().getAdresse().getOrt(), contentFont));
			cell.setBorderColor(BaseColor.GRAY);
			tbl.addCell(cell);

			/* Ablageort (Regal / Tablar) */
			cell = new PdfPCell(new Phrase("Ablageort", headingFont));
			cell.setBorderColor(BaseColor.GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase(produktTyp.getAblage(), contentFont));
			cell.setBorderColor(BaseColor.GRAY);
			tbl.addCell(cell);

			pdfDocument.add(tbl);

			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			String zeitpunkt = sdf.format(new Date());

			Font datumFont = FontFactory.getFont("Courier", 8, BaseColor.GRAY);

			paragraph = new Paragraph("\n\nDatum: " + zeitpunkt, datumFont);
			paragraph.setAlignment(Element.ALIGN_RIGHT);

			pdfDocument.add(paragraph);

			pdfDocument.close();

		} catch (Exception e) {
			logger.error("Fehler bei der Generierung der Infokarte als PDF: ", e);
			throw new RuntimeException(e);
		} finally {
			if (fos != null) {
				fos.close();
			}
		}

	}

	/**
	 * Erstellt die Bestellung als PDF.
	 *
	 * @param bestellung
	 * @throws Exception
	 */
	public void printBestellungAlsPdf(Bestellung bestellung) throws Exception {

		SimpleDateFormat sdfName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
		FileOutputStream fos = null;

		try {

			int cnt = 1;

			Properties props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("printer.properties"));
			String outputDir = ausgabeVerzeichnis(props);
			String defaultFileName = props.getProperty("bestellung_filiale_default_file_name");

			Document pdfDocument = new Document();
			fos = new FileOutputStream(
					outputDir + File.separator + sdfName.format(new Date()) + "-" + defaultFileName + ".pdf");
			PdfWriter.getInstance(pdfDocument, fos);
			pdfDocument.open();

			/* Fonts */
			Font anschriftFont = FontFactory.getFont("Courier", 8, BaseColor.GRAY);
			Font filialeAdresseFont = FontFactory.getFont("Courier", 6, BaseColor.GRAY);
			Font titelFont = FontFactory.getFont("Courier", 14, BaseColor.GRAY);
			titelFont.setStyle(Font.BOLD);
			Font zwischenzeileFont = FontFactory.getFont("Courier", 6, BaseColor.GRAY);
			Font tableFont = FontFactory.getFont("Courier", 8, BaseColor.GRAY);
			Font headingFont = FontFactory.getFont("Courier", 8, BaseColor.GRAY);
			headingFont.setStyle(Font.BOLD);

			/* Anschrift zusammenstellen */
			StringBuilder sb = new StringBuilder();

			sb.append(props.getProperty("anschrift_zeile_1")).append("\n");
			sb.append(props.getProperty("anschrift_zeile_2")).append("\n");
			sb.append(props.getProperty("anschrift_zeile_3")).append("\n");
			sb.append(props.getProperty("anschrift_zeile_4")).append("\n\n\n\n\n");

			Paragraph paragraph = new Paragraph(sb.toString(), anschriftFont);
			paragraph.setAlignment(Element.ALIGN_RIGHT);
			pdfDocument.add(paragraph);

			/* Tabelle */
			PdfPTable tbl = new PdfPTable(5);

			/* Spaltenbreite definieren */
			float[] columnWidths = new float[] { 5f, 25f, 25f, 35f, 10f };
			tbl.setWidths(columnWidths);

			PdfPCell cell = new PdfPCell();

			/* Besteller */
			Moebelhaus moebelhaus = bestellung.getBesteller();
			cell = new PdfPCell(
					new Phrase(moebelhaus.getName() + " [" + moebelhaus.getMoebelhausCode() + "]\n", tableFont));
			cell.setColspan(5);
			cell.setBorderWidth(0);
			tbl.addCell(cell);

			/* Filiale-Adresse & Telefon */
			cell = new PdfPCell(new Phrase(
					moebelhaus.getAdresse().getStrasse() + ", " + moebelhaus.getAdresse().getPlz() + " "
							+ moebelhaus.getAdresse().getOrt() + ", Tel. " + moebelhaus.getKontakt().getTelefon(),
					filialeAdresseFont));
			cell.setColspan(5);
			cell.setBorderWidth(0);
			tbl.addCell(cell);

			/* Zwischenzeile */
			cell = new PdfPCell(new Phrase(" ", zwischenzeileFont));
			cell.setColspan(5);
			cell.setBorderWidth(0);
			tbl.addCell(cell);

			/* Zwischenzeile */
			cell = new PdfPCell(new Phrase(" ", zwischenzeileFont));
			cell.setColspan(5);
			cell.setBorderWidth(0);
			tbl.addCell(cell);

			/* Bestellung-Nr. */
			cell = new PdfPCell(new Phrase("BESTELLUNG NR. " + bestellung.getId(), titelFont));
			cell.setColspan(5);
			cell.setBorderWidth(0);
			tbl.addCell(cell);

			/* Zwischenzeile */
			cell = new PdfPCell(new Phrase(" ", zwischenzeileFont));
			cell.setColspan(5);
			cell.setBorderWidth(0);
			tbl.addCell(cell);

			/* Überschriften */
			cell = new PdfPCell(new Phrase("Nr.", headingFont));
			cell.setBorderColor(BaseColor.GRAY);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase("Lieferant", headingFont));
			cell.setBorderColor(BaseColor.GRAY);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase("Produkt-Typ Name", headingFont));
			cell.setBorderColor(BaseColor.GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase("Produkt-Typ Code", headingFont));
			cell.setBorderColor(BaseColor.GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase("Anzahl", headingFont));
			cell.setBorderColor(BaseColor.GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl.addCell(cell);

			for (BestellungPosition pos : bestellung.getBestellungPositionListe()) {

				cell = new PdfPCell(new Phrase("" + cnt++, tableFont));
				cell.setBorderColor(BaseColor.GRAY);
				tbl.addCell(cell);

				cell = new PdfPCell(new Phrase(pos.getProduktTyp().getLieferant().getName(), tableFont));
				cell.setBorderColor(BaseColor.GRAY);
				tbl.addCell(cell);

				cell = new PdfPCell(new Phrase(pos.getProduktTyp().getName(), tableFont));
				cell.setBorderColor(BaseColor.GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tbl.addCell(cell);

				cell = new PdfPCell(new Phrase(pos.getProduktTyp().getTypCode(), tableFont));
				cell.setBorderColor(BaseColor.GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tbl.addCell(cell);

				cell = new PdfPCell(new Phrase("" + pos.getAnzahl(), tableFont));
				cell.setBorderColor(BaseColor.GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tbl.addCell(cell);
			}

			pdfDocument.add(tbl);

			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy ' um ' HH:mm:ss");
			String zeitpunkt = sdf.format(new Date());

			Font datumFont = FontFactory.getFont("Courier", 8, BaseColor.GRAY);

			paragraph = new Paragraph("\n\nDatum / Zeit: " + zeitpunkt, datumFont);
			paragraph.setAlignment(Element.ALIGN_RIGHT);

			pdfDocument.add(paragraph);

			pdfDocument.close();

		} catch (Exception e) {
			logger.error("Fehler bei der Generierung der Bestellung als PDF: ", e);
			throw new RuntimeException(e);
		} finally {
			if (fos != null) {
				fos.close();
			}
		}

	}

	/**
	 * Erstellt die Rücksendung als PDF.
	 *
	 * @param ruecksendung
	 */
	public void printRuecksendungAlsPdf(Ruecksendung ruecksendung) throws Exception {

		SimpleDateFormat sdfName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
		FileOutputStream fos = null;

		try {

			int cnt = 1;

			Properties props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("printer.properties"));
			String outputDir = ausgabeVerzeichnis(props);
			String defaultFileName = props.getProperty("ruecksendung_filiale_default_file_name");

			Document pdfDocument = new Document();
			fos = new FileOutputStream(
					outputDir + File.separator + sdfName.format(new Date()) + "-" + defaultFileName + ".pdf");
			PdfWriter.getInstance(pdfDocument, fos);
			pdfDocument.open();

			/* Fonts */
			Font anschriftFont = FontFactory.getFont("Courier", 8, BaseColor.GRAY);
			Font filialeAdresseFont = FontFactory.getFont("Courier", 6, BaseColor.GRAY);
			Font titelFont = FontFactory.getFont("Courier", 14, BaseColor.GRAY);
			titelFont.setStyle(Font.BOLD);
			Font zwischenzeileFont = FontFactory.getFont("Courier", 6, BaseColor.GRAY);
			Font tableFont = FontFactory.getFont("Courier", 8, BaseColor.GRAY);
			Font headingFont = FontFactory.getFont("Courier", 8, BaseColor.GRAY);
			headingFont.setStyle(Font.BOLD);

			/* Anschrift zusammenstellen */
			StringBuilder sb = new StringBuilder();

			sb.append(props.getProperty("anschrift_zeile_1")).append("\n");
			sb.append(props.getProperty("anschrift_zeile_2")).append("\n");
			sb.append(props.getProperty("anschrift_zeile_3")).append("\n");
			sb.append(props.getProperty("anschrift_zeile_4")).append("\n\n\n\n\n");

			Paragraph paragraph = new Paragraph(sb.toString(), anschriftFont);
			paragraph.setAlignment(Element.ALIGN_RIGHT);
			pdfDocument.add(paragraph);

			/* Tabelle */
			PdfPTable tbl = new PdfPTable(4);

			/* Spaltenbreite definieren */
			float[] columnWidths = new float[] { 5f, 30f, 55f, 10f };
			tbl.setWidths(columnWidths);

			PdfPCell cell = new PdfPCell();

			/* Besteller */
			Moebelhaus moebelhaus = ruecksendung.getAbsender();
			cell = new PdfPCell(
					new Phrase(moebelhaus.getName() + " [" + moebelhaus.getMoebelhausCode() + "]\n", tableFont));
			cell.setColspan(4);
			cell.setBorderWidth(0);
			tbl.addCell(cell);

			/* Filiale-Adresse & Telefon */
			cell = new PdfPCell(new Phrase(
					moebelhaus.getAdresse().getStrasse() + ", " + moebelhaus.getAdresse().getPlz() + " "
							+ moebelhaus.getAdresse().getOrt() + ", Tel. " + moebelhaus.getKontakt().getTelefon(),
					filialeAdresseFont));
			cell.setColspan(4);
			cell.setBorderWidth(0);
			tbl.addCell(cell);

			/* Zwischenzeile */
			cell = new PdfPCell(new Phrase(" ", zwischenzeileFont));
			cell.setColspan(4);
			cell.setBorderWidth(0);
			tbl.addCell(cell);

			/* Zwischenzeile */
			cell = new PdfPCell(new Phrase(" ", zwischenzeileFont));
			cell.setColspan(4);
			cell.setBorderWidth(0);
			tbl.addCell(cell);

			/* Bestellung-Nr. */
			cell = new PdfPCell(new Phrase("RUECKSENDUNG NR. " + ruecksendung.getId(), titelFont));
			cell.setColspan(4);
			cell.setBorderWidth(0);
			tbl.addCell(cell);

			/* Zwischenzeile */
			cell = new PdfPCell(new Phrase(" ", zwischenzeileFont));
			cell.setColspan(4);
			cell.setBorderWidth(0);
			tbl.addCell(cell);

			/* Überschriften */
			cell = new PdfPCell(new Phrase("Nr.", headingFont));
			cell.setBorderColor(BaseColor.GRAY);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase("Produkt Code", headingFont));
			cell.setBorderColor(BaseColor.GRAY);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase("Bemerkung / Begründung", headingFont));
			cell.setBorderColor(BaseColor.GRAY);
			tbl.addCell(cell);

			cell = new PdfPCell(new Phrase("Anzahl", headingFont));
			cell.setBorderColor(BaseColor.GRAY);
			tbl.addCell(cell);

			for (RuecksendungPosition pos : ruecksendung.getRuecksendungPositionListe()) {

				cell = new PdfPCell(new Phrase("" + cnt++, tableFont));
				cell.setBorderColor(BaseColor.GRAY);
				tbl.addCell(cell);

				cell = new PdfPCell(new Phrase(pos.getProduktTyp().getTypCode(), tableFont));
				cell.setBorderColor(BaseColor.GRAY);
				tbl.addCell(cell);

				cell = new PdfPCell(new Phrase(pos.getBemerkung(), tableFont));
				cell.setBorderColor(BaseColor.GRAY);
				tbl.addCell(cell);

				cell = new PdfPCell(new Phrase("" + pos.getAnzahl(), tableFont));
				cell.setBorderColor(BaseColor.GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tbl.addCell(cell);
			}

			pdfDocument.add(tbl);

			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy ' um ' HH:mm:ss");
			String zeitpunkt = sdf.format(new Date());

			Font datumFont = FontFactory.getFont("Courier", 8, BaseColor.GRAY);

			paragraph = new Paragraph("\n\nDatum / Zeit: " + zeitpunkt, datumFont);
			paragraph.setAlignment(Element.ALIGN_RIGHT);

			pdfDocument.add(paragraph);

			pdfDocument.close();

		} catch (Exception e) {
			logger.error("Fehler bei der Generierung der Rücksendung als PDF: ", e);
			throw new RuntimeException(e);
		} finally {
			if (fos != null) {
				fos.close();
			}
		}

	}

	/**
	 * Helper-Methode
	 */
	private String ausgabeVerzeichnis(Properties props) {

		String outputDir = props.getProperty("output_dir");

		File f = new File(outputDir);

		if (!f.exists() && !f.isDirectory()) {
			logger.warn("WARNUNG: Das Verzeichnis \'" + outputDir + "\' existiert nicht! "
					+ "Bitte passen Sie den Wert für das Ausgabeverzechnis in der \'printer.properties\'-Datei (Property \'output_dir\') "
					+ "des Subprojekts \'moebelhaus-pdfprinter\' an. "
					+ "Inzwischen wird das Home-Verzeichnis des Benutzers als Ausgabeverzeichnis verwendet.");

			outputDir = System.getProperty("user.home");
		}

		return outputDir;
	}
}
