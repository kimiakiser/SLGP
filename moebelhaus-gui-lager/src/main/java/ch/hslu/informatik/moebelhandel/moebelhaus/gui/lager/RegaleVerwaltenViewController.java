package ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Regal;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class RegaleVerwaltenViewController implements Initializable {

    private static Logger logger = LogManager.getLogger(RegaleVerwaltenViewController.class);

    private static final String ERROR_MSG_EINGABE_NICHT_KORREKT = "Eingabe nicht korrekt.";
    private static final String ERROR_MSG_SPEICHERN_MISSLUNGEN = "Das Regal konnte nicht gespeichert werden.";
    private static final String ERROR_MSG_LOESCHEN_MISSLUNGEN = "Das Regal konnte nicht gelöscht werden.";

    @FXML
    private Label lblError;

    @FXML
    private TextField txtRegalNummer;

    @FXML
    private ComboBox<Integer> cmbAnzahlTablare;

    @FXML
    private TableView<Regal> tblRegale;

    @FXML
    private TableColumn<Regal, String> colNummer;

    @FXML
    private TableColumn<Regal, String> colBezeichnung;

    @FXML
    private TableColumn<Regal, Integer> colAnzahlTablare;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	try {

    		lblError.setText("");

    		/* ComboBox initialisieren */
    		ObservableList<Integer> anzahlTablareListe = FXCollections.observableArrayList();

    		for (int i = 0; i < 16; i++) {
    			anzahlTablareListe.add(i + 1);
    		}

    		cmbAnzahlTablare.setItems(anzahlTablareListe);
    		cmbAnzahlTablare.getSelectionModel().select(0);

    		colNummer.setCellValueFactory(new PropertyValueFactory<Regal, String>("nummer"));
	        colBezeichnung.setCellValueFactory(new PropertyValueFactory<Regal, String>("bezeichnung"));
	        colAnzahlTablare.setCellValueFactory(new PropertyValueFactory<Regal, Integer>("anzahlTablare"));

	        tblRegale.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Regal>() {

	        	@Override
	        	public void changed(ObservableValue<? extends Regal> observable, Regal oldValue, Regal newValue) {

	        		if (newValue != null) {
            	       txtRegalNummer.setText("" + newValue.getNummer());
            	       cmbAnzahlTablare.getSelectionModel().select(newValue.getAnzahlTablare() - 1);
	        		}
	        	}
	        });

	        updateTable();
	        
    	} catch (Exception e) {
			logger.error("Fehler bei der Initialisierung der View: ", e);
			throw new RuntimeException(e);
		}    
    }

    private void updateTable() {

        try {
            List<Regal> tempListe = Context.getInstance().getMoebelhausLagerService().alleRegale();

            TreeSet<Regal> ts = new TreeSet<>(new Comparator<Regal>() {

                @Override
                public int compare(Regal a, Regal b) {
                    return a.getNummer() - b.getNummer();
                }
            });

            ts.addAll(tempListe);

            ObservableList<Regal> regalListe = FXCollections.observableArrayList();

            regalListe.addAll(ts);

            tblRegale.setItems(regalListe);
        } catch (Exception e) {
        	logger.error("Fehler beim Update Tabelle: ", e);
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void speichern() {

        int nummer = 0;

        String strNummer = txtRegalNummer.getText();

        if (!isValid(strNummer)) {
            lblError.setText(ERROR_MSG_EINGABE_NICHT_KORREKT);
            return;
        }

        try {
            nummer = Integer.parseInt(strNummer);
        } catch (NumberFormatException e1) {
            lblError.setText(ERROR_MSG_EINGABE_NICHT_KORREKT);
            return;
        }

        try {

            /*
             * Prüfen od ein Regal mit der eingegebenen Bezeichnung bereits
             * vorhanden ist
             */
            Regal regal = Context.getInstance().getMoebelhausLagerService().regalByNummer(nummer);

            if (regal != null) {
                /*
                 * Ein Regal mit der eingegebenen Bezeichnung bereits vorhanden:
                 * Löschen, damit das Regal anschliessend neu angelegt wird.
                 */

                Context.getInstance().getMoebelhausLagerService().regalLoeschen(regal);

                tblRegale.getItems().clear();
            }

            int anzahlTablare = cmbAnzahlTablare.getSelectionModel().getSelectedIndex() + 1;

            Context.getInstance().getMoebelhausLagerService().regalHinzufuegen(nummer, anzahlTablare);

            /* Tabelle updaten */
            updateTable();

            txtRegalNummer.setText("");
            cmbAnzahlTablare.getSelectionModel().select(0);

        } catch (Exception e) {
            logger.error("Fehler beim Speichern des Regals: ", e);
            lblError.setText(ERROR_MSG_SPEICHERN_MISSLUNGEN);
        }
    }

    @FXML
    private void regalLoeschen() {
        Regal regal = tblRegale.getSelectionModel().getSelectedItem();

        if (regal != null) {
            try {
                Context.getInstance().getMoebelhausLagerService().regalLoeschen(regal);
                updateTable();
                txtRegalNummer.setText("");
                cmbAnzahlTablare.getSelectionModel().select(0);
            } catch (Exception e) {
                logger.error("Fehler beim Löschen des Regals: ", e);
                lblError.setText(ERROR_MSG_LOESCHEN_MISSLUNGEN);
                return;
            }
        }
    }

    private boolean isValid(String str) {
        return str != null && !str.trim().isEmpty();
    }
}