package ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager.wrapper.LieferantenWrapper;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Adresse;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Kontakt;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lieferant;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class LieferantenVewaltenViewController implements Initializable {

    public static final String ERROR_MSG_PLZ_EINGABE_NICHT_KORREKT = "Die Eingabe für Postleitzahl ist nicht korrekt.";
    public static final String ERROR_MSG_EINGABE_NICHT_KORREKT = "Die Eingabe ist entweder nicht vollständig oder nicht korrekt (alle Felder sind 'required')";

    private static Logger logger = LogManager.getLogger(LieferantenVewaltenViewController.class);

    @FXML
    private Label lblError;

    @FXML
    private Label lblCmbLieferanten;

    @FXML
    private Label lblTxtLieferanten;

    @FXML
    private ComboBox<Lieferant> cmbLieferanten;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtStrasse;

    @FXML
    private TextField txtPlz;

    @FXML
    private TextField txtOrt;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtTelefon;

    @FXML
    private TableView<LieferantenWrapper> tblLieferanten;

    @FXML
    private TableColumn<LieferantenWrapper, Integer> colNummer;
    @FXML
    private TableColumn<LieferantenWrapper, String> colName;
    @FXML
    private TableColumn<LieferantenWrapper, String> colStrasse;
    @FXML
    private TableColumn<LieferantenWrapper, Integer> colPlz;
    @FXML
    private TableColumn<LieferantenWrapper, String> colOrt;
    @FXML
    private TableColumn<LieferantenWrapper, String> colEmail;
    @FXML
    private TableColumn<LieferantenWrapper, String> colTelefon;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    	try {
    		/* Tabelle konfigurieren */
    		colNummer.setCellValueFactory(new PropertyValueFactory<LieferantenWrapper, Integer>("nummer"));
    		colName.setCellValueFactory(new PropertyValueFactory<LieferantenWrapper, String>("name"));
    		colStrasse.setCellValueFactory(new PropertyValueFactory<LieferantenWrapper, String>("strasse"));
    		colPlz.setCellValueFactory(new PropertyValueFactory<LieferantenWrapper, Integer>("plz"));
    		colOrt.setCellValueFactory(new PropertyValueFactory<LieferantenWrapper, String>("ort"));
    		colEmail.setCellValueFactory(new PropertyValueFactory<LieferantenWrapper, String>("Email"));
    		colTelefon.setCellValueFactory(new PropertyValueFactory<LieferantenWrapper, String>("telefon"));

    	 	tblLieferanten.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<LieferantenWrapper>() {

    	 		@Override
    	 		public void changed(ObservableValue<? extends LieferantenWrapper> observable, LieferantenWrapper oldValue, LieferantenWrapper newValue) {
    	 			if (newValue != null) {
    	 				updateView();
    	 			}
    	 		}
    	 	});	

        /* Tabelle updaten */
        updateTabelle();

    	} catch (Exception e) {
			logger.error("Fehler bei der Initialisierung der View: ", e);
			throw new RuntimeException(e);
		}
    }

    private void updateTabelle() {

        try {

            List<Lieferant> liferanten = Context.getInstance().getMoebelhausLagerService().alleLieferanten();

            List<LieferantenWrapper> wrapperListe = new ArrayList<>();

            int nummer = 1;
            for (Lieferant l : liferanten) {
                wrapperListe.add(new LieferantenWrapper(nummer++, l));
            }

            tblLieferanten.getItems().clear();
            tblLieferanten.getItems().addAll(wrapperListe);

            if (tblLieferanten.getItems().size() > 0) {
                tblLieferanten.getSelectionModel().select(0);
                updateView();
            }

        } catch (Exception e) {
        	logger.error("Fehler bei Update Tabelle: ", e);
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void reset() {

        lblError.setText("");
        tblLieferanten.getSelectionModel().clearSelection();

        txtName.setText("");
        txtStrasse.setText("");
        txtPlz.setText("");
        txtOrt.setText("");
        txtEmail.setText("");
        txtTelefon.setText("");
    }

    private void updateView() {

        lblError.setText("");

        Lieferant selectedLieferant = null;

        if (tblLieferanten.getSelectionModel().getSelectedItem() != null) {
            selectedLieferant = tblLieferanten.getSelectionModel().getSelectedItem().getLieferant();
        }

        if (selectedLieferant != null) {
            txtName.setText(selectedLieferant.getName());
            txtStrasse.setText(selectedLieferant.getAdresse().getStrasse());
            txtPlz.setText("" + selectedLieferant.getAdresse().getPlz());
            txtOrt.setText(selectedLieferant.getAdresse().getOrt());
            txtEmail.setText(selectedLieferant.getKontakt().getEmail());
            txtTelefon.setText(selectedLieferant.getKontakt().getTelefon());
        } else {
            reset();
        }
    }

    @FXML
    private void neuenLieferantenErfassen() {
        lblError.setText("");
        reset();
        txtName.requestFocus();
    }

    @FXML
    private void speichern() {
        lblError.setText("");
        if (eingabeValid()) {

            if (tblLieferanten.getSelectionModel().getSelectedItem() == null) {

                /* Neuen Lieferanten einfügen */
                String name = txtName.getText();
                String strasse = txtStrasse.getText();
                int plz = Integer.parseInt(txtPlz.getText());
                String ort = txtOrt.getText();
                String email = txtEmail.getText();
                String telefon = txtTelefon.getText();

                Lieferant lieferant = new Lieferant(name, new Adresse(strasse, plz, ort), new Kontakt(email, telefon));

                try {
                    Context.getInstance().getMoebelhausLagerService().lieferantenHinzufuegen(lieferant);
                } catch (Exception e) {
                    logger.error("Fehler beim Hinzufügen eines neuen Lieferanten: ", e);
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Alert Lager");
                    alert.setHeaderText("Information");
                    alert.setContentText("Das Hinzufügen des neuen Lieferanten ist misslungen.");
                    alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
                    alert.showAndWait();
                }
            } else {

                /* Den selektierten Lieferanten updaten */
                String name = txtName.getText();
                String strasse = txtStrasse.getText();
                int plz = Integer.parseInt(txtPlz.getText());
                String ort = txtOrt.getText();
                String email = txtEmail.getText();
                String telefon = txtTelefon.getText();

                Lieferant lieferant = tblLieferanten.getSelectionModel().getSelectedItem().getLieferant();

                lieferant.setName(name);
                lieferant.setAdresse(new Adresse(strasse, plz, ort));
                lieferant.setKontakt(new Kontakt(email, telefon));

                try {
                    Context.getInstance().getMoebelhausLagerService().lieferantenAktualisieren(lieferant);
                } catch (Exception e) {
                    logger.error("Fehler beim Hinzufügen eines neuen Lieferanten: ", e);
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Alert Lager");
                    alert.setHeaderText("Information");
                    alert.setContentText("Das Aktualisieren des ausgewählten Lieferanten ist misslungen.");
                    alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
                    alert.showAndWait();
                }
            }

            updateTabelle();
            reset();
            txtName.requestFocus();
        }
    }

    @FXML
    private void loeschen() {

        if (tblLieferanten.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        Lieferant lieferant = tblLieferanten.getSelectionModel().getSelectedItem().getLieferant();

        if (lieferant != null) {
            try {
                Context.getInstance().getMoebelhausLagerService().lieferantenLoeschen(lieferant);
                updateTabelle();
                updateView();
            } catch (Exception e) {
                logger.error("Fehler beim Löschen des Lieferanten: ", e);
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Alert Lager");
                alert.setHeaderText("Information");
                alert.setContentText("Das Löschen des Lieferanten ist misslungen.");
                alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
                alert.showAndWait();
            }
        }
    }

    private boolean eingabeValid() {
        lblError.setText("");
        if (isValid(txtName.getText()) && isValid(txtStrasse.getText()) && isValid(txtPlz.getText())
                && isValid(txtOrt.getText()) && isValid(txtEmail.getText()) && isValid(txtTelefon.getText())) {

            /* Prüfen, od die PLZ korrekt eingegeben wurde */
            try {
                Integer.parseInt(txtPlz.getText());
                return true;
            } catch (NumberFormatException e) {
                lblError.setText(ERROR_MSG_PLZ_EINGABE_NICHT_KORREKT);
                return false;
            }
        } else {
            lblError.setText(ERROR_MSG_EINGABE_NICHT_KORREKT);
            return false;
        }
    }

    private boolean isValid(String str) {
        return str != null && str.trim().length() > 0;
    }
}