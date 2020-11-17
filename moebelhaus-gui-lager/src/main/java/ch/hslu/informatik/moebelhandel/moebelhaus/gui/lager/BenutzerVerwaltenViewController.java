package ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager.wrapper.BenutzerWrapper;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Adresse;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Credentials;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Kontakt;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.RolleTyp;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BenutzerVerwaltenViewController implements Initializable {

    public static final String ERROR_MSG_PLZ_EINGABE_NICHT_KORREKT = "Die Eingabe für Postleitzahl ist nicht korrekt.";
    public static final String ERROR_MSG_EINGABE_NICHT_KORREKT = "Die Eingabe ist entweder nicht vollständig oder nicht korrekt (alle Felder sind 'required')";

    private static Logger logger = LogManager.getLogger(BenutzerVerwaltenViewController.class);

    @FXML
    private Label lblError;

    @FXML
    private ComboBox<RolleTyp> cmbRolle;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtVorname;

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
    private TextField txtBenutzername;

    @FXML
    private TextField txtKennwort;

    @FXML
    private TableView<BenutzerWrapper> tblBenutzer;

    @FXML
    private TableColumn<BenutzerWrapper, Integer> colNummer;

    @FXML
    private TableColumn<BenutzerWrapper, String> colName;

    @FXML
    private TableColumn<BenutzerWrapper, String> colVorname;

    @FXML
    private TableColumn<BenutzerWrapper, String> colStrasse;

    @FXML
    private TableColumn<BenutzerWrapper, Integer> colPlz;

    @FXML
    private TableColumn<BenutzerWrapper, String> colOrt;

    @FXML
    private TableColumn<BenutzerWrapper, String> colEmail;

    @FXML
    private TableColumn<BenutzerWrapper, String> colTelefon;

    @FXML
    private TableColumn<BenutzerWrapper, String> colBenutzername;

    @FXML
    private TableColumn<BenutzerWrapper, String> colKennwort;

    @FXML
    private TableColumn<BenutzerWrapper, String> colRolle;

    @FXML
    private Button btnLoeschen;

    // @Override
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            lblError.setText("");

            /* cmbRolle initialisieren */
            List<RolleTyp> rolleListe = new ArrayList<>();

            rolleListe = Context.getInstance().getBenutzerService().alleRollen();

            cmbRolle.getItems().addAll(rolleListe);

            if (tblBenutzer.getItems().size() > 0 && cmbRolle.getItems().size() > 0) {
                cmbRolle.getSelectionModel().select(0);
            }

            /* Tabelle konfigurieren */
            colNummer.setCellValueFactory(new PropertyValueFactory<BenutzerWrapper, Integer>("nummer"));
            colName.setCellValueFactory(new PropertyValueFactory<BenutzerWrapper, String>("name"));
            colVorname.setCellValueFactory(new PropertyValueFactory<BenutzerWrapper, String>("vorname"));
            colStrasse.setCellValueFactory(new PropertyValueFactory<BenutzerWrapper, String>("strasse"));
            colPlz.setCellValueFactory(new PropertyValueFactory<BenutzerWrapper, Integer>("plz"));
            colOrt.setCellValueFactory(new PropertyValueFactory<BenutzerWrapper, String>("ort"));
            colEmail.setCellValueFactory(new PropertyValueFactory<BenutzerWrapper, String>("email"));
            colTelefon.setCellValueFactory(new PropertyValueFactory<BenutzerWrapper, String>("telefon"));
            colBenutzername.setCellValueFactory(new PropertyValueFactory<BenutzerWrapper, String>("benutzername"));
            colKennwort.setCellValueFactory(new PropertyValueFactory<BenutzerWrapper, String>("kennwort"));
            colRolle.setCellValueFactory(new PropertyValueFactory<BenutzerWrapper, String>("rolle"));

            tblBenutzer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BenutzerWrapper>() {

                @Override
                public void changed(ObservableValue<? extends BenutzerWrapper> observable, BenutzerWrapper oldValue,
                        BenutzerWrapper newValue) {
                    if (newValue != null) {
                        updateView();
                    }
                }
            });

            updateTabelle();

            btnLoeschen.disableProperty().bind(Bindings.size(tblBenutzer.getSelectionModel().getSelectedItems()).isEqualTo(0));

        } catch (Exception e) {
			logger.error("Fehler bei der Initialisierung der View: ", e);
			throw new RuntimeException(e);
		}
    }

    private void updateTabelle() {

        try {
            List<Benutzer> benutzerListe = Context.getInstance().getBenutzerService().alleBenutzer();

            if (benutzerListe.size() > 0) {
                List<BenutzerWrapper> wrapperListe = new ArrayList<>();

                int nummer = 1;

                for (Benutzer benutzer : benutzerListe) {
                    wrapperListe.add(new BenutzerWrapper(nummer++, benutzer));
                }

                tblBenutzer.getItems().clear();
                tblBenutzer.getItems().addAll(wrapperListe);
                tblBenutzer.getSelectionModel().select(0);

                updateView();
            }
        } catch (Exception e) {
            logger.error("Fehler bei der Aktualisierung der Tabelle: ", e);
            throw new RuntimeException(e);
        }

    }

    private void updateView() {

        lblError.setText("");

        if (tblBenutzer.getSelectionModel().getSelectedItem() == null) {

            cmbRolle.getSelectionModel().clearSelection();
            txtName.setText("");
            txtVorname.setText("");
            txtStrasse.setText("");
            txtPlz.setText("");
            txtOrt.setText("");
            txtEmail.setText("");
            txtTelefon.setText("");
            txtBenutzername.setText("");
            txtKennwort.setText("");

        } else {

            Benutzer benutzer = tblBenutzer.getSelectionModel().getSelectedItem().getBenutzer();

            cmbRolle.getSelectionModel().select(benutzer.getRolle());
            txtName.setText(benutzer.getNachname());
            txtVorname.setText(benutzer.getVorname());
            txtStrasse.setText(benutzer.getAdresse().getStrasse());
            txtPlz.setText("" + benutzer.getAdresse().getPlz());
            txtOrt.setText(benutzer.getAdresse().getOrt());
            txtEmail.setText(benutzer.getKontakt().getEmail());
            txtTelefon.setText(benutzer.getKontakt().getTelefon());
            txtBenutzername.setText(benutzer.getCredentials().getBenutzername());
            txtKennwort.setText(benutzer.getCredentials().getPasswort());
        }
    }

    @FXML
    private void neuenBenutzerErfassen() {
        reset();
        txtName.requestFocus();
    }

    @FXML
    private void speichern() {

        if (eingabeValid()) {

            if (tblBenutzer.getSelectionModel().getSelectedItem() == null) {

                /* Neuen Benutzer einfügen */
                String name = txtName.getText();
                String vorname = txtVorname.getText();
                String strasse = txtStrasse.getText();
                int plz = Integer.parseInt(txtPlz.getText());
                String ort = txtOrt.getText();
                String email = txtEmail.getText();
                String telefon = txtTelefon.getText();
                String benutzername = txtBenutzername.getText();
                String kennwort = txtKennwort.getText();
                RolleTyp rolle = cmbRolle.getSelectionModel().getSelectedItem();

                Benutzer benutzer = new Benutzer(name, vorname, new Adresse(strasse, plz, ort),
                        new Kontakt(email, telefon), new Credentials(benutzername, kennwort), rolle);

                try {
                    Context.getInstance().getBenutzerService().benutzerHinzufuegen(benutzer);
                } catch (Exception e) {
                    logger.error("Fehler beim Hinzufügen eines neuen Benutzers: ", e);
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Alert Lager");
                    alert.setHeaderText("Information");
                    alert.setContentText("Das Hinzufügen des neuen Benutzers ist misslungen.");
                    alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
                    alert.showAndWait();
                }
            } else {

                /* Den selektierten Benutzer updaten */
                String name = txtName.getText();
                String vorname = txtVorname.getText();
                String strasse = txtStrasse.getText();
                int plz = Integer.parseInt(txtPlz.getText());
                String ort = txtOrt.getText();
                String email = txtEmail.getText();
                String telefon = txtTelefon.getText();
                String benutzername = txtBenutzername.getText();
                String kennwort = txtKennwort.getText();
                RolleTyp rolle = cmbRolle.getSelectionModel().getSelectedItem();

                Benutzer benutzer = tblBenutzer.getSelectionModel().getSelectedItem().getBenutzer();

                benutzer.setNachname(name);
                benutzer.setVorname(vorname);
                benutzer.setAdresse(new Adresse(strasse, plz, ort));
                benutzer.setKontakt(new Kontakt(email, telefon));
                benutzer.getCredentials().setBenutzername(benutzername);
                benutzer.getCredentials().setPasswort(kennwort);
                benutzer.setRolle(rolle);

                try {
                    Context.getInstance().getBenutzerService().benutzerAktualisieren(benutzer);
                } catch (Exception e) {
                    logger.error("Fehler beim Hinzufügen eines neuen Benutzers: ", e);
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Alert Lager");
                    alert.setHeaderText("Information");
                    alert.setContentText("Das Aktualisieren des ausgewählten Benutzers ist misslungen.");
                    alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
                    alert.showAndWait();
                }
            }

            updateTabelle();
            reset();
            txtName.requestFocus();
        }
    }

    private boolean eingabeValid() {

        lblError.setText("");
        if (isValid(txtName.getText()) && isValid(txtVorname.getText()) && isValid(txtStrasse.getText())
                && isValid(txtPlz.getText()) && isValid(txtOrt.getText()) && isValid(txtEmail.getText())
                && isValid(txtTelefon.getText()) && isValid(txtEmail.getText()) && isValid(txtKennwort.getText())) {

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

    @FXML
    private void reset() {

        tblBenutzer.getSelectionModel().clearSelection();

        cmbRolle.getSelectionModel().select(0);

        txtName.setText("");
        txtVorname.setText("");
        txtStrasse.setText("");
        txtPlz.setText("");
        txtOrt.setText("");
        txtEmail.setText("");
        txtTelefon.setText("");
        txtBenutzername.setText("");
        txtKennwort.setText("");
    }

    @FXML
    private void loeschen() {

        if (tblBenutzer.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        Benutzer benutzer = tblBenutzer.getSelectionModel().getSelectedItem().getBenutzer();

        if (benutzer != null) {
            try {
                Context.getInstance().getBenutzerService().benutzerLoeschen(benutzer);
                updateTabelle();
                updateView();
            } catch (Exception e) {
                logger.error("Fehler beim Löschen des Benutzers: ", e);
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Alert Lager");
                alert.setHeaderText("Information");
                alert.setContentText("Das Löschen des Benutzers ist misslungen.");
                alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
                alert.showAndWait();
            }
        }
    }

    private boolean isValid(String str) {
        return str != null && str.trim().length() > 0;
    }
}