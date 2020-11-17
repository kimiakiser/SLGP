package ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager.wrapper.BestellungPositionWrapper;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Bestellung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.BestellungPosition;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class LieferungAnnehmenViewController implements Initializable {

    private static Logger logger = LogManager.getLogger(LieferungAnnehmenViewController.class);

    @FXML
    private Label lblBestellungDetails;

    @FXML
    private TextField txtBestellungNummer;
    @FXML
    private TextField txtLieferungNummer;
    @FXML
    private TextField txtProduktTypCode;
    @FXML
    private TextField txtAnzahl;
    
    @FXML
    private Button button;
    
    @FXML
    private Button btnEinlagern;
    
    @FXML
    private Button btnAllesEinlagern;

    @FXML
    private TableView<BestellungPositionWrapper> tblBestellung;
    @FXML
    private TableColumn<BestellungPositionWrapper, Integer> colNummer;
    @FXML
    private TableColumn<BestellungPositionWrapper, String> colLieferant;
    @FXML
    private TableColumn<BestellungPositionWrapper, String> colProduktname;
    @FXML
    private TableColumn<BestellungPositionWrapper, String> colProduktCode;
    @FXML
    private TableColumn<BestellungPositionWrapper, Integer> colAnzahl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	try {
    		lblBestellungDetails.setAlignment(Pos.BASELINE_RIGHT);

    		/* tblBestellung konfigurieren */
    		colNummer.setCellValueFactory(new PropertyValueFactory<BestellungPositionWrapper, Integer>("nummer"));
    		colLieferant.setCellValueFactory(new PropertyValueFactory<BestellungPositionWrapper, String>("lieferant"));
    		colProduktname.setCellValueFactory(new PropertyValueFactory<BestellungPositionWrapper, String>("produktname"));
    		colProduktCode.setCellValueFactory(new PropertyValueFactory<BestellungPositionWrapper, String>("produktCode"));
    		colAnzahl.setCellValueFactory(new PropertyValueFactory<BestellungPositionWrapper, Integer>("anzahl"));

    		tblBestellung.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BestellungPositionWrapper>() {

    			@Override
    			public void changed(ObservableValue<? extends BestellungPositionWrapper> observable, BestellungPositionWrapper oldValue, BestellungPositionWrapper newValue) {

    				if (newValue != null) {
    					updateView();
    				}
    			}
    		});

    		btnEinlagern.disableProperty().bind(Bindings.size(tblBestellung.getSelectionModel().getSelectedItems()).isEqualTo(0));
    	
    	} catch (Exception e) {
			logger.error("Fehler bei der Initialisierung der View: ", e);
			throw new RuntimeException(e);
		}
    }

    @FXML
    private void bestellungAnzeigen() {

    	txtLieferungNummer.setText("");
    	tblBestellung.getItems().clear();
        reset();

        String strNr = txtBestellungNummer.getText();

        if (isValid(strNr)) {

            try {
            	long id = Long.parseLong(strNr);

                /* Bestellung holen */
                Bestellung bestellung = Context.getInstance().getMoebelhausLagerService().findeBestellungById(id);

                /* Bestellung-Nr. und Datum anzeigen */
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                lblBestellungDetails.setText("Bestellung Nr. " + bestellung.getId() + " vom " + sdf.format(new Date(bestellung.getDatum().getTimeInMillis())));
                
                for (BestellungPosition bPosition : bestellung.getBestellungPositionListe()) {
                    tblBestellung.getItems().add(new BestellungPositionWrapper(tblBestellung.getItems().size() + 1, bPosition));
                }

            } catch (NumberFormatException e) {
      			Alert alert = new Alert(AlertType.WARNING);
    			alert.setTitle("Alert Lager");
    			alert.setHeaderText("Information");
    			alert.setContentText("Eingabe für die Bestellung-Nummer ist nicht korrekt.");
    			alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
    			alert.showAndWait();
                return;
                
            } catch (Exception e) {
                logger.error("Fehler bei der Suche nach der Bestellung: ", e);
      			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Alert Lager");
    			alert.setHeaderText("Information");
    			alert.setContentText("Bestellung konnte nicht angezeigt werden");
    			alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
    			alert.showAndWait();
                return;
            }
        }
    }
    
    @FXML
    private void lieferungAnzeigen() {
    	
    	txtBestellungNummer.setText("");
    	tblBestellung.getItems().clear();
        reset();

        String strNr = txtLieferungNummer.getText();

        if (isValid(strNr)) {

            try {
            	long id = Long.parseLong(strNr);

                /* Bestellung holen */
                Bestellung bestellung = Context.getInstance().getWebService().lieferungByNummer(id);

                /* Bestellung-Nr. und Datum anzeigen */
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                lblBestellungDetails.setText("Lieferung Nr. " + txtLieferungNummer.getText() +" von Bestellung Nr. " + bestellung.getId() + " vom " + sdf.format(new Date(bestellung.getDatum().getTimeInMillis())));
                
                for (BestellungPosition bPosition : bestellung.getBestellungPositionListe()) {
                    tblBestellung.getItems().add(new BestellungPositionWrapper(tblBestellung.getItems().size() + 1, bPosition));
                }

            } catch (NumberFormatException e) {
      			Alert alert = new Alert(AlertType.WARNING);
    			alert.setTitle("Alert Lager");
    			alert.setHeaderText("Information");
    			alert.setContentText("Eingabe für die Lieferung-Nummer ist nicht korrekt.");
    			alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
    			alert.showAndWait();
                return;
                
            } catch (Exception e) {
                logger.error("Fehler bei der Suche nach der Lieferung: ", e);
      			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Alert Lager");
    			alert.setHeaderText("Information");
    			alert.setContentText("Lieferung konnte nicht angezeigt werden");
    			alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
    			alert.showAndWait();
                return;
            }
        }
    }
    
    @FXML
    private void einlagern() {
        try {
            int anzahl = 0;

            try {

                anzahl = Integer.parseInt(txtAnzahl.getText());
                ProduktTyp pTyp = Context.getInstance().getMoebelhausLagerService().findeByProduktTypCode(tblBestellung.getSelectionModel().getSelectedItem().getBestellungPosition().getProduktTyp().getTypCode());
                Context.getInstance().getMoebelhausLagerService().produktEinlagern(pTyp, anzahl);

                /* BestellungPosition aus der Tabelle entferenen */
                tblBestellung.getItems().remove(tblBestellung.getSelectionModel().getSelectedItem());
                
                Alert alert = new Alert(AlertType.INFORMATION);
    			alert.setTitle("Alert Lager");
    			alert.setHeaderText("Information");
    			alert.setContentText("Position erfolgreich eingelagert.");
    			alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
    			alert.showAndWait();
    			
                reset();

            } catch (NumberFormatException e) {
    			Alert alert = new Alert(AlertType.WARNING);
    			alert.setTitle("Alert Lager");
    			alert.setHeaderText("Information");
    			alert.setContentText("Eingabe für die Anzahl ist nicht korrekt.");
    			alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
    			alert.showAndWait();
                return;
            }
        } catch (Exception e) {
        	logger.error("Fehler beim Einlager der Position: " + e);
        	
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Alert Lager");
			alert.setHeaderText("Information");
			alert.setContentText("Fehler beim Einlagern der Position.");
			alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
			alert.showAndWait();
        }
    }

    @FXML
    private void allesEinlagern() {
        try {
            int anzahl = 0;

            	for (int i = 0; i < tblBestellung.getItems().size(); i ++) {
            		anzahl = tblBestellung.getItems().get(i).getBestellungPosition().getAnzahl();
            		ProduktTyp pTyp = Context.getInstance().getMoebelhausLagerService().findeByProduktTypCode(tblBestellung.getSelectionModel().getSelectedItem().getBestellungPosition().getProduktTyp().getTypCode());
            		Context.getInstance().getMoebelhausLagerService().produktEinlagern(pTyp, anzahl);
            	}
            	
            	tblBestellung.getItems().clear();
                
      			Alert alert = new Alert(AlertType.INFORMATION);
    			alert.setTitle("Alert Lager");
    			alert.setHeaderText("Information");
    			alert.setContentText("Alle Positionen erfolgreich eingelagert.");
    			alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
    			alert.showAndWait();
    			
    			reset();
      
        } catch (Exception e) {
        	logger.error("Fehler beim Einlagern aller Positionen: " + e);
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Alert Lager");
			alert.setHeaderText("Information");
			alert.setContentText("Fehler beim Einlager aller Positionen.");
			alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
			alert.showAndWait();
        }
    }

    private boolean isValid(String str) {
        return str != null && str.trim().length() > 0;
    }

    private void updateView() {

        BestellungPositionWrapper wrapper = tblBestellung.getSelectionModel().getSelectedItem();

        if (wrapper != null) {
            BestellungPosition p = wrapper.getBestellungPosition();
            txtProduktTypCode.setText(p.getProduktTyp().getTypCode());
            txtAnzahl.setText("" + p.getAnzahl());
        }
    }
    
    @FXML
    private void resetAll() {
    	try {
    		tblBestellung.getItems().clear();
    		txtBestellungNummer.setText("");
    		txtLieferungNummer.setText("");
    		reset();
    		
    	} catch (Exception e) {
			logger.error("Fehler beim Zurücksetzen der View: ", e);
			throw new RuntimeException(e);
		}
    }
    
    private void reset() {
        lblBestellungDetails.setText("");
        txtProduktTypCode.setText("");
        txtAnzahl.setText("0");
        tblBestellung.getSelectionModel().clearSelection();
    }
}