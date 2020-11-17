package ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse;

import java.net.URL;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse.wapper.RechnungPositionWrapper;
import ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse.wapper.RechnungWrapper;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Rechnung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.RechnungPosition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class RechnungenViewController implements Initializable {

	private static Logger logger = LogManager.getLogger(RechnungenViewController.class);

	@FXML
	private TextField txtRechnungsnummer;
	
	@FXML
	private Button button;
	
	@FXML
	private DatePicker datePicker;
	
	@FXML
	private TableView<RechnungWrapper> tblRechnung;
	@FXML
	private TableColumn<RechnungWrapper, Integer> colNr;
	@FXML
	private TableColumn<RechnungWrapper, Long> colId;
	@FXML
	private TableColumn<RechnungWrapper, String> colDatum;
	@FXML
	private TableColumn<RechnungWrapper, String> colBenutzer;
	@FXML
	private TableColumn<RechnungWrapper, Double> colBetrag;
	@FXML
	private TableColumn<RechnungWrapper, Integer> colAnzahlProdukte;

	@FXML
	private TableView<RechnungPositionWrapper> tblRechnungPosition;
	@FXML
	private TableColumn<RechnungPositionWrapper, Integer> colNummer;
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
			/* TableView Rechungen konfigurieren */
			colNr.setCellValueFactory(new PropertyValueFactory<RechnungWrapper, Integer>("nr"));
			colId.setCellValueFactory(new PropertyValueFactory<RechnungWrapper, Long>("id"));
			colDatum.setCellValueFactory(new PropertyValueFactory<RechnungWrapper, String>("datum"));
			colBenutzer.setCellValueFactory(new PropertyValueFactory<RechnungWrapper, String>("benutzer"));
			colBetrag.setCellValueFactory(new PropertyValueFactory<RechnungWrapper, Double>("betrag"));
			colAnzahlProdukte.setCellValueFactory(new PropertyValueFactory<RechnungWrapper, Integer>("anzahlProdukte"));
			
			ObservableList<RechnungWrapper> rechnungListe = FXCollections.observableArrayList();
			tblRechnung.setItems(rechnungListe);
			
			/* TableView RechnungPosition konfigurieren */
			colNummer.setCellValueFactory(new PropertyValueFactory<RechnungPositionWrapper, Integer>("nummer"));
			colProduktname.setCellValueFactory(new PropertyValueFactory<RechnungPositionWrapper, String>("produktname"));
			colProduktTypCode.setCellValueFactory(new PropertyValueFactory<RechnungPositionWrapper, String>("produktCode"));
			colBeschreibung.setCellValueFactory(new PropertyValueFactory<RechnungPositionWrapper, String>("beschreibung"));
			colLieferant.setCellValueFactory(new PropertyValueFactory<RechnungPositionWrapper, String>("lieferant"));
			colPreis.setCellValueFactory(new PropertyValueFactory<RechnungPositionWrapper, Double>("preis"));
			colAnzahl.setCellValueFactory(new PropertyValueFactory<RechnungPositionWrapper, Integer>("anzahl"));
			
			ObservableList<RechnungPositionWrapper> rechnungPositionListe = FXCollections.observableArrayList();
			tblRechnungPosition.setItems(rechnungPositionListe);
			
			tblRechnung.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RechnungWrapper>() {

                @Override
                public void changed(ObservableValue<? extends RechnungWrapper> observable, RechnungWrapper oldValue, RechnungWrapper newValue) {

                    if (newValue != null) {
                    	positionenAnzeigen();
                    }
                }
            });
			
		} catch (Exception e) {
			logger.error("Fehler bei der Initialisierung der View: ", e);
			throw new RuntimeException(e);
		}
	}

	@FXML
	private void rechnungSuchen() {
		
		try {
			/* Alle Listen leeren */
			tblRechnung.getItems().clear();
			tblRechnungPosition.getItems().clear();
		
			/* Rechnung nach Nummer suchen */
			int index = 0;
			
			try {
				long rechnungId = Long.parseLong(txtRechnungsnummer.getText());
				Rechnung rechnung = Context.getInstance().getKasseService().findByRechnungNummer(rechnungId);
				if (rechnung == null) {
					throw new NullPointerException();
				}
				RechnungWrapper rechnungWrapper = new RechnungWrapper(tblRechnung.getItems().size() + 1, rechnung);
				tblRechnung.getItems().add(rechnungWrapper);
				index = 1;
			} catch (Exception e) {
			}
			
			/* Rechnung nach Benutzer und Datum suchen */
			if (index == 0) {
			
				try {
					GregorianCalendar datum = new GregorianCalendar(datePicker.getValue().getYear(), datePicker.getValue().getMonthValue() - 1, datePicker.getValue().getDayOfMonth());

					List<Rechnung> rechnungL = Context.getInstance().getKasseService().findByBenutzerUndDatum(Context.getInstance().getBenutzer() , datum);

					for (Rechnung rechnung : rechnungL) {
						RechnungWrapper rechnungWrapper = new RechnungWrapper(tblRechnung.getItems().size() + 1, rechnung);
						tblRechnung.getItems().add(rechnungWrapper);
					}
					
					if (rechnungL.size() == 0) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Alert Kasse");
						alert.setHeaderText("Information");
						alert.setContentText("Keine Rechnung gefunden.");
						alert.showAndWait();
					}
			
				} catch (Exception e) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Alert Kasse");
					alert.setHeaderText("Information");
					alert.setContentText("Bitte gültige Rechungsnummer oder gültiges Datum eingeben. ");
					alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
					alert.showAndWait();
					return;
				} 
			} 
			
		} catch (Exception e) {
			logger.error("Fehler beim Suchen von Rechnungen: ", e);
			throw new RuntimeException(e);
		}
	}
	
	@FXML
	private void positionenAnzeigen() {
		
		/* Rechnungpositionen der ausgew‰hlten Rechnung anzeigen */
		try {
			tblRechnungPosition.getItems().clear();
			
			RechnungWrapper rechnungWrapper = tblRechnung.getSelectionModel().getSelectedItem();
			int i = 1;
			for (RechnungPosition rPos : rechnungWrapper.getRechnungPositionListe()) {
				RechnungPositionWrapper rPosWrapper = new RechnungPositionWrapper(i++, rPos);
				tblRechnungPosition.getItems().add(rPosWrapper);
			}
			
		} catch (Exception e) {
			logger.error("Fehler beim Anzeigen der Rechnungspositionen: ", e);
			throw new RuntimeException(e);
		}
	}
	
	@FXML
	private void abbrechen() {
		try {
			/* Alle Listen leeren */
			tblRechnung.getItems().clear();
			tblRechnungPosition.getItems().clear();
		} catch (Exception e) {
			logger.error("Fehler beim Abbrechen: ", e);
			throw new RuntimeException(e);
		}
	}
}