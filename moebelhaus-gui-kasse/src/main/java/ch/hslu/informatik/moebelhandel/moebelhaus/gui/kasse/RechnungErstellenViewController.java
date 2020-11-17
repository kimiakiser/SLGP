package ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse.wapper.RechnungPositionWrapper;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Produkt;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Rechnung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.RechnungPosition;
import ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse.Context;

public class RechnungErstellenViewController implements Initializable{
	
	private static Logger logger = LogManager.getLogger(RechnungErstellenViewController.class);
	
	@FXML
	private TextField txtProduktCode;
	
	@FXML
	private Button button;
	
	@FXML
	private TableView<RechnungPositionWrapper> tblRechnungList;	
	@FXML
	private TableColumn<RechnungPositionWrapper, Integer> colNr;	
	@FXML
	private TableColumn<RechnungPositionWrapper, String> colProduktname;
	@FXML
	private TableColumn<RechnungPositionWrapper, String> colProduktTypCode;
	@FXML
	private TableColumn<RechnungPositionWrapper, String> colBeschreibung;	
	@FXML
	private TableColumn<RechnungPositionWrapper, String> colLieferant;	
	@FXML
	private TableColumn<RechnungPositionWrapper, Double> colPreis;
	@FXML
	private TableColumn<RechnungPositionWrapper, Integer> colAnzahl;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			/* Liste für Rechnnug erstellen */
			List<Produkt> listeVerkauf = new ArrayList<Produkt>();
			Context.getInstance().getContentMap().put("listeVerkauf", listeVerkauf);
			
			/* TableView konfigurieren */
			colNr.setCellValueFactory(new PropertyValueFactory<RechnungPositionWrapper, Integer>("nummer"));
			colProduktname.setCellValueFactory(new PropertyValueFactory<RechnungPositionWrapper, String>("produktname"));
			colProduktTypCode.setCellValueFactory(new PropertyValueFactory<RechnungPositionWrapper, String>("produktCode"));
			colBeschreibung.setCellValueFactory(new PropertyValueFactory<RechnungPositionWrapper, String>("beschreibung"));
			colLieferant.setCellValueFactory(new PropertyValueFactory<RechnungPositionWrapper, String>("lieferant"));
			colPreis.setCellValueFactory(new PropertyValueFactory<RechnungPositionWrapper, Double>("preis"));
			colAnzahl.setCellValueFactory(new PropertyValueFactory<RechnungPositionWrapper, Integer>("anzahl"));
			
			ObservableList<RechnungPositionWrapper> rechnungListe = FXCollections.observableArrayList();
			tblRechnungList.setItems(rechnungListe);
			
		} catch (Exception e) {
			logger.error("Fehler bei der Initialisierung der View: ", e);
			throw new RuntimeException(e);
		}
	}
	
	public void Scanner() {
		
		try {
			Context.getInstance().getContentMap().put("txtProduktCode", txtProduktCode);
			
			AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/ScannerView.fxml"));

			root.setMinWidth(660);
			root.setMinHeight(150);

			root.setPrefWidth(660);
			root.setPrefHeight(150);

			root.setMaxWidth(660);
			root.setMaxHeight(150);

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			scene.getStylesheets().add("/styles/Styles.css");
			stage.setTitle("Scanner");
			stage.setScene(scene);
			stage.show();
			
		} catch (Exception e) {
			logger.error("Fehler beim Laden des Scanners: ", e);
			throw new RuntimeException(e);
		}
	}
	
	public void produktHinzufuegen() {
		
		List<Produkt> listeVerkauf = (List) Context.getInstance().getContentMap().get("listeVerkauf");
		Produkt produkt;
		RechnungPosition rPos;
			
		/* Produktcode auf Gültigkeit prüfen */
		try {
			produkt = Context.getInstance().getKasseService().findByProduktCode(Long.parseLong(txtProduktCode.getText()));
			rPos = new RechnungPosition(produkt.getTyp(), 1);
			
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Alert Kasse");
			alert.setHeaderText("Information");
			alert.setContentText("Bitte einen gültigen Produktcode eingeben.");
			alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
			alert.showAndWait();
			return;
		}
			
		/* Prüfen ob Produktcode bereits auf der Liste ist, falls nicht wird dieser hinzugefügt */
		try {	
			boolean check = true;
			for (Produkt p : listeVerkauf) {
				if (produkt.equals(p)) {
					check = false;
					break;
				}
			}
			
			if (check == true) {
				listeVerkauf.add(produkt);
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Alert Kasse");
				alert.setHeaderText("Information");
				alert.setContentText("Dieser Produktecode wurde bereits hinzugefügt.");
				alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
				alert.showAndWait();
				return;
			}
			
		} catch (Exception e) {
			logger.error("Fehler beim Prüfen des Produkecode: ", e);
		}
	
		/* Produkt wird zur Tabelle hinzugefügt. Falls der Produkte-Typ bereits vorhanden ist, wird nur die Anzahl um eins erhöht */
		try {
			int index = -1;
			
			for (int i = 0; i < tblRechnungList.getItems().size(); i++) {
				
				RechnungPositionWrapper wrapper = tblRechnungList.getItems().get(i);
				if (wrapper.getRechnungPosition().getProduktTyp().getTypCode().equals(rPos.getProduktTyp().getTypCode())) {
					index = i;
					break;
				}
			}

			if (index != -1) {

				int anzahlNeu = rPos.getAnzahl() + tblRechnungList.getItems().get(index).getRechnungPosition().getAnzahl();
		
				rPos.setAnzahl(anzahlNeu);

				RechnungPositionWrapper rPosWrapper = new RechnungPositionWrapper(tblRechnungList.getItems().size(), rPos);

				tblRechnungList.getItems().remove(index);
				tblRechnungList.getItems().add(index, rPosWrapper);

			} else {
				RechnungPositionWrapper rPosWrapper = new RechnungPositionWrapper(tblRechnungList.getItems().size() + 1, rPos);
				tblRechnungList.getItems().add(rPosWrapper);
			}
		
		} catch (Exception e) {
			logger.error("Fehler beim Aktualisieren der Tabelle: ", e);	
		}	
	}
	
	public void produktEntfernen() {
		
		List<Produkt> listeVerkauf = (List) Context.getInstance().getContentMap().get("listeVerkauf");
		Produkt produkt;
		RechnungPosition rPos;
			
		/* Produktcode auf Gültigkeit prüfen */
		try {
			produkt = Context.getInstance().getKasseService().findByProduktCode(Long.parseLong(txtProduktCode.getText()));
			rPos = new RechnungPosition(produkt.getTyp(), 1);
			
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Alert Kasse");
			alert.setHeaderText("Information");
			alert.setContentText("Bitte einen gültigen Produktcode eingeben.");
			alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
			alert.showAndWait();
			return;
		}
			
		/* Prüfen ob Produktcode bereits auf der Liste ist, falls nicht wird dieser hinzugefügt */
		try {	
			boolean check = false;
			for (Produkt p : listeVerkauf) {
				if (produkt.equals(p)) {
					check = true;
					break;
				}
			}

			if (check == true) {
				listeVerkauf.remove(produkt);
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Alert Kasse");
				alert.setHeaderText("Information");
				alert.setContentText("Dieser Produktecode ist in der Liste nicht enthalten.");
				alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
				alert.showAndWait();
				return;
			}
			
		} catch (Exception e) {
			logger.error("Fehler beim Prüfen des Produkecode: ", e);
		}
	
		/* Produkt wird zur Tabelle hinzugefügt. Falls der Produkte-Typ bereits vorhanden ist, wird nur die Anzahl um eins erhöht */
		try {
			int index = -1;
			
			for (int i = 0; i < tblRechnungList.getItems().size(); i++) {
				RechnungPositionWrapper wrapper = tblRechnungList.getItems().get(i);
				if (wrapper.getRechnungPosition().getProduktTyp().getTypCode().equals(rPos.getProduktTyp().getTypCode())) {
					index = i;
					break;
				}
			}

			if (index != -1) {

				int anzahlNeu = tblRechnungList.getItems().get(index).getRechnungPosition().getAnzahl() -1;
				
				rPos.setAnzahl(anzahlNeu);

				RechnungPositionWrapper rPosWrapper = new RechnungPositionWrapper(tblRechnungList.getItems().size(), rPos);

				if (anzahlNeu != 0) {
				tblRechnungList.getItems().remove(index); 
				tblRechnungList.getItems().add(index, rPosWrapper); 
				}else {
					tblRechnungList.getItems().remove(index);	
				}

			} else {
				RechnungPositionWrapper rPosWrapper = new RechnungPositionWrapper(tblRechnungList.getItems().size() - 1, rPos);
				tblRechnungList.getItems().add(rPosWrapper);
			}
		
		} catch (Exception e) {
			logger.error("Fehler beim Löschen eines Produktes in der Tabelle: ", e);	
		}	
	}
	
	public void rechnungErstellen() {
		try {
			List<Produkt> listeVerkauf = (List) Context.getInstance().getContentMap().get("listeVerkauf");
			
			/* Rechnung generieren */
			if (listeVerkauf.isEmpty()) {
				throw new Exception("Produkteliste ist leer.");
			} else {
				Rechnung rechnung = Context.getInstance().getKasseService().verkaufen(listeVerkauf, Context.getInstance().getBenutzer());
				Context.getInstance().getPdfPrinter().printRechnungAlsPdf(rechnung);
			}
			
			/* Alle Listen leeren */
			listeVerkauf.clear();
			tblRechnungList.getItems().clear();
			txtProduktCode.clear();
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Alert Kasse");
			alert.setHeaderText("Information");
			alert.setContentText("Die Rechnung wurde erfolgreich generiert.");
			alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
			alert.showAndWait();
			
		} catch (Exception e) {
			logger.error("Fehler beim Generieren der Rechnung: ", e);
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Alert Kasse");
			alert.setHeaderText("Information");
			alert.setContentText("Das Generieren der Renchung ist misslungen.");
			alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
			alert.showAndWait();
		}
	}
	
	public void abbrechen() {
		try {
			List<Produkt> listeVerkauf = (List) Context.getInstance().getContentMap().get("listeVerkauf");
			
			/* Alle Listen leeren */
			listeVerkauf.clear();
			tblRechnungList.getItems().clear();
			txtProduktCode.clear();
			
		} catch (Exception e) {
			logger.error("Fehler beim Abbrechen: ", e);	
			throw new RuntimeException();
		}
	}
}
