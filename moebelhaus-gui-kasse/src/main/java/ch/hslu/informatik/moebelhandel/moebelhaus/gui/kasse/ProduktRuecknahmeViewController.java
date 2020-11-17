package ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse;

import java.net.URL;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse.wapper.ProduktRuecknahmeWrapper;
import ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse.wapper.RechnungPositionWrapper;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktRuecknahme;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Rechnung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.RechnungPosition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProduktRuecknahmeViewController implements Initializable {

    private static Logger logger = LogManager.getLogger(ProduktRuecknahmeViewController.class);
    
    @FXML
    private TextField txtRechnungsNr;

    @FXML
    private ComboBox<Integer> cmbAnzahl;

    @FXML
    private TextArea txtBemerkung;

    @FXML
    private Button button;

    @FXML
    private TableView<ProduktRuecknahmeWrapper> tblRuecknahme;
    @FXML
    private TableColumn<ProduktRuecknahmeWrapper, Integer> colNr;
    @FXML
    private TableColumn<ProduktRuecknahmeWrapper, Integer> colRechnungsNr;
    @FXML
    private TableColumn<ProduktRuecknahmeWrapper, String> colProduktCode;
    @FXML
    private TableColumn<ProduktRuecknahmeWrapper, Integer> colMenge;
    @FXML
    private TableColumn<ProduktRuecknahmeWrapper, String> colBemerkung;
    
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
	    	colNr.setCellValueFactory(new PropertyValueFactory<ProduktRuecknahmeWrapper, Integer>("nummer"));
	    	colRechnungsNr.setCellValueFactory(new PropertyValueFactory<ProduktRuecknahmeWrapper, Integer>("rechnungsNummer"));
	    	colProduktCode.setCellValueFactory(new PropertyValueFactory<ProduktRuecknahmeWrapper, String>("produktTypCode"));
	    	colMenge.setCellValueFactory(new PropertyValueFactory<ProduktRuecknahmeWrapper, Integer>("anzahl"));
	    	colBemerkung.setCellValueFactory(new PropertyValueFactory<ProduktRuecknahmeWrapper, String>("bemerkung"));
	    	
	    	ObservableList<ProduktRuecknahmeWrapper> produktRuecknahmeListe = FXCollections.observableArrayList();
	    	tblRuecknahme.setItems(produktRuecknahmeListe);
	    
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
			
    	} catch (Exception e) {
			logger.error("Fehler bei der Initialisierung der View: ", e);
			throw new RuntimeException(e);
		}
    }
    
    @FXML
	private void rechnungAnzeigen() {
    	
		try {
			/* Alle Listen leeren */
			tblRechnungPosition.getItems().clear();
			tblRuecknahme.getItems().clear();
			
			/*  ComboBox zurücksetzen */
			cmbAnzahl.getItems().clear();
			
			/* Rechnungpositionen der Rechnung anzeigen */
			Rechnung rechnung = Context.getInstance().getKasseService().findByRechnungNummer(Integer.parseInt(txtRechnungsNr.getText()));
			
			int i = 1;
			for (RechnungPosition rPos : rechnung.getRechnungPositionListe()) {
				RechnungPositionWrapper rPosWrapper = new RechnungPositionWrapper(i++, rPos);
				tblRechnungPosition.getItems().add(rPosWrapper);
			}
			
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Alert Kasse");
			alert.setHeaderText("Information");
			alert.setContentText("Bitte gültige Rechungsnummer eingeben.");
			alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
			alert.showAndWait();
		}
	}
    
    @FXML
    private void initComboBoxe() {
    	
    	try {
    		int anzahl = tblRechnungPosition.getSelectionModel().getSelectedItem().getRechnungPosition().getAnzahl();
    		if (anzahl != cmbAnzahl.getItems().size()) {
    			ObservableList<Integer> oListe = FXCollections.observableArrayList();
    		
    			for (int i = 1; i <= anzahl; i++) {
    				oListe.add(i);
    			}
				cmbAnzahl.setItems(oListe);
				if (oListe.size() > 0) {
					cmbAnzahl.getSelectionModel().select(0);
				}
    		}
    		
    	} catch (Exception e) {
    		logger.error("Fehler beim Füllen der ComboBox: ", e);
    	}
    }
    
    @FXML
    private void ruecknahmeGenerieren() {

    	try {
			tblRuecknahme.getItems().clear();
			
			Rechnung rechnung = Context.getInstance().getKasseService().findByRechnungNummer(Integer.parseInt(txtRechnungsNr.getText()));
			ProduktTyp pTyp = tblRechnungPosition.getSelectionModel().getSelectedItem().getProduktTyp();
			int anzahl = cmbAnzahl.getSelectionModel().getSelectedItem();
			String bemerkung = txtBemerkung.getText();
			
			for (RechnungPosition rpos : rechnung.getRechnungPositionListe()) {
				if (rpos.getProduktTyp().getTypCode().equals(pTyp.getTypCode()) && rpos.getAnzahl() >= anzahl) {
					ProduktRuecknahme ruecknahme = new ProduktRuecknahme(rechnung, pTyp, new GregorianCalendar(), anzahl, bemerkung);
					ProduktRuecknahmeWrapper ruecknahmeWrapper = new ProduktRuecknahmeWrapper(tblRuecknahme.getItems().size() + 1, ruecknahme);
					tblRuecknahme.getItems().add(ruecknahmeWrapper);
					break;
				}
			}
			
			if (tblRuecknahme.getItems().size() == 0) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Alert Kasse");
				alert.setHeaderText("Information");
				alert.setContentText("Bitte gültige Informationen eingeben oder Produkt aus der Liste auswählen.");
				alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
				alert.showAndWait();
			}

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Alert Kasse");
			alert.setHeaderText("Information");
			alert.setContentText("Bitte gültige Informationen eingeben oder Produkt aus der Liste auswählen.");
			alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
			alert.showAndWait();
		}	
    }

    @FXML
    private void ruecknahmeBestaetigen () {

    	try {
    		Context.getInstance().getKasseService().produktZuruecknehmen(tblRuecknahme.getItems().get(0).getProduktRuecknahme());
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Alert Kasse");
			alert.setHeaderText("Information");
			alert.setContentText("Rücknahme erfolgreich erstellt.");
			alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
			alert.showAndWait();
			reset();
			
		} catch (Exception e) {
			logger.error("Fehler beim Speichern der Rücknahme: ", e);
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Alert Kasse");
			alert.setHeaderText("Information");
			alert.setContentText("Fehler beim Speichern der Rücknahme.");
			alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
			alert.showAndWait();
		}	
    }
    
    @FXML
    private void reset() {

    	try {
			/* Alle Listen leeren */
			tblRechnungPosition.getItems().clear();
			tblRuecknahme.getItems().clear();
			
			/* Alle Textfelder und ComboBox zurücksetzen */
			txtRechnungsNr.clear();
			cmbAnzahl.getItems().clear();
			txtBemerkung.clear();
			txtBemerkung.setPromptText("Geben Sie an, wieso das Produkt zurückgenomen wird.");
			
		} catch (Exception e) {
			logger.error("Fehler beim Abbrechen: ", e);	
			throw new RuntimeException();
		} 	
    }
}