package ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager.wrapper.ProduktTypWrapper;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lieferant;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Regal;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Tablar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class ProduktTypVerwaltenViewController implements Initializable {

    private static Logger logger = LogManager.getLogger(ProduktTypVerwaltenViewController.class);

    public static final String ERROR_MSG_LOESCHEN_MISSLUNGEN = "Produkt-Typ konnte nicht gelöscht werden.";
    public static final String ERROR_MSG_SPEICHERN_MISSLUNGEN = "Produkt-Typ konnte nicht gespeichert werden.";
    public static final String ERROR_MSG_UPDATE_MISSLUNGEN = "Produkt-Typ konnte nicht updatet werden.";
    public static final String ERROR_MSG_PREIS_EINGABE_NICHT_KORREKT = "Die Preisangabe ist nicht korrekt.";
    public static final String ERROR_MSG_EINGABE_NICHT_KORREKT = "Die Eingabe ist entweder nicht vollständig oder nicht korrekt (alle Felder sind 'required')";

    @FXML
    private Label lblError;

    @FXML
    private TextField txtLieferanten;

    @FXML
    private ComboBox<Lieferant> cmbLieferanten;

    @FXML
    private TextField txtProduktTypName;
    @FXML
    private TextField txtProduktTypCode;

    @FXML
    private ComboBox<Integer> cmbMin;
    @FXML
    private ComboBox<Integer> cmbMax;
    @FXML
    private ComboBox<Regal> cmbRegal;
    @FXML
    private ComboBox<Tablar> cmbTablar;

    @FXML
    private TextField txtPreis;
    @FXML
    private TextField txtBeschreibung;

    @FXML
    private TableView<ProduktTypWrapper> tblProduktTyp;

    @FXML
    private TableColumn<ProduktTypWrapper, Integer> colNummer;
    @FXML
    private TableColumn<ProduktTypWrapper, String> colLieferant;
    @FXML
    private TableColumn<ProduktTypWrapper, String> colProduktTypCode;
    @FXML
    private TableColumn<ProduktTypWrapper, Integer> colMinBestand;
    @FXML
    private TableColumn<ProduktTypWrapper, Integer> colMaxBestand;
    @FXML
    private TableColumn<ProduktTypWrapper, String> colAblage;
    @FXML
    private TableColumn<ProduktTypWrapper, String> colBeschreibung;
    @FXML
    private TableColumn<ProduktTypWrapper, Double> colPreis;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /* cmbLieferanten unsichtbar machen */
        cmbLieferanten.setVisible(false);

        try {

            lblError.setText("");

            /* cmbLieferanten initialisieren */
            List<Lieferant> liste = Context.getInstance().getMoebelhausLagerService().alleLieferanten();

            cmbLieferanten.getItems().clear();
            cmbLieferanten.getItems().addAll(liste);
            if (liste.size() > 0) {
                cmbLieferanten.getSelectionModel().select(0);
            }

            /* cmbMin und cmbMax initialisieren */
            ObservableList<Integer> minListe = FXCollections.observableArrayList();
            ObservableList<Integer> maxListe = FXCollections.observableArrayList();

            for (int i = 0; i < 10; i++) {
                minListe.add(i + 1);
            }

            maxListe.add(2);
            maxListe.add(5);
            maxListe.add(10);
            maxListe.add(20);
            maxListe.add(50);
            maxListe.add(100);
            maxListe.add(200);

            cmbMin.setItems(minListe);
            cmbMax.setItems(maxListe);

            cmbMin.getSelectionModel().select(0);
            cmbMax.getSelectionModel().select(0);

            /* cmbRegal und cmbTablar initialisieren */
            ObservableList<Regal> regalListe = FXCollections.observableArrayList();
            ObservableList<Tablar> tablarListe = FXCollections.observableArrayList();

            TreeSet<Regal> tsRegal = new TreeSet<>(new Comparator<Regal>() {

                @Override
                public int compare(Regal a, Regal b) {
                    return a.getNummer() - b.getNummer();
                }
            });

            tsRegal.addAll(Context.getInstance().getMoebelhausLagerService().alleRegale());
            regalListe.addAll(tsRegal);

            cmbRegal.setItems(regalListe);
            cmbRegal.getSelectionModel().select(0);

            Regal selectedRegal = cmbRegal.getSelectionModel().getSelectedItem();

            if (selectedRegal != null) {

                TreeSet<Tablar> tsTablar = new TreeSet<>(new Comparator<Tablar>() {

                    @Override
                    public int compare(Tablar a, Tablar b) {
                        return a.getBezeichnung().compareTo(b.getBezeichnung());
                    }
                });

                tsTablar.addAll(selectedRegal.getTablarListe());
                tablarListe.addAll(tsTablar);

                cmbTablar.setItems(tablarListe);
                cmbTablar.getSelectionModel().select(0);
            }

            cmbRegal.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Regal>() {

                @Override
                public void changed(ObservableValue<? extends Regal> observable, Regal oldValue, Regal newValue) {

                    if (newValue != null) {

                        List<Tablar> tablarListe = newValue.getTablarListe();
                        TreeSet<Tablar> tsTablar = new TreeSet<>(new Comparator<Tablar>() {

                            @Override
                            public int compare(Tablar a, Tablar b) {
                                return a.getBezeichnung().compareTo(b.getBezeichnung());
                            }
                        });

                        tsTablar.addAll(tablarListe);
                        cmbTablar.getItems().clear();
                        cmbTablar.getItems().addAll(tsTablar);
                        cmbTablar.getSelectionModel().select(0);
                    }

                }

            });

            /* Tabelle */
            colNummer.setCellValueFactory(new PropertyValueFactory<ProduktTypWrapper, Integer>("nr"));
            colLieferant.setCellValueFactory(new PropertyValueFactory<ProduktTypWrapper, String>("lieferantName"));
            colProduktTypCode.setCellValueFactory(new PropertyValueFactory<ProduktTypWrapper, String>("produktTypCode"));

            colMinBestand.setCellValueFactory(new PropertyValueFactory<ProduktTypWrapper, Integer>("minBestand"));
            colMaxBestand.setCellValueFactory(new PropertyValueFactory<ProduktTypWrapper, Integer>("maxBestand"));
            colAblage.setCellValueFactory(new PropertyValueFactory<ProduktTypWrapper, String>("ablage"));
            colBeschreibung.setCellValueFactory(new PropertyValueFactory<ProduktTypWrapper, String>("beschreibung"));
            colPreis.setCellValueFactory(new PropertyValueFactory<ProduktTypWrapper, Double>("preis"));

            tblProduktTyp.getSelectionModel().select(0);

            updateTable();
            updateView();

            tblProduktTyp.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProduktTypWrapper>() {

                        @Override
                        public void changed(ObservableValue<? extends ProduktTypWrapper> observable,
                                ProduktTypWrapper oldValue, ProduktTypWrapper newValue) {
                            if (newValue != null) {
                                updateView();
                            }
                        }
                    });

        } catch (Exception e) {
			logger.error("Fehler bei der Initialisierung der View: ", e);
			throw new RuntimeException(e);
		}
    }

    private void updateTable() {

        lblError.setText("");

        try {

            List<ProduktTyp> produktTypListe = Context.getInstance().getMoebelhausLagerService().produktTypAlle();

            tblProduktTyp.getItems().clear();

            List<ProduktTypWrapper> liste = new ArrayList<>();

            int nummer = 1;

            for (ProduktTyp pTyp : produktTypListe) {
                liste.add(new ProduktTypWrapper(nummer++, pTyp, Context.getInstance().getMoebelhausLagerService().produktBestand(pTyp).size(), 0));
            }

            tblProduktTyp.getItems().addAll(liste);
            tblProduktTyp.getSelectionModel().select(0);

        } catch (Exception e) {
            logger.error("Fehler beim Update der Tabelle: ", e);
            throw new RuntimeException(e);
        }
    }

    private void updateView() {

        lblError.setText("");

        try {
            ProduktTypWrapper wrapper = tblProduktTyp.getSelectionModel().getSelectedItem();

            if (wrapper == null) {
                cmbLieferanten.getSelectionModel().select(0);
                txtLieferanten.setText("");
                txtProduktTypName.setText("");
                txtProduktTypCode.setText("");
                txtPreis.setText("0.0");
                txtBeschreibung.setText("");
                cmbMin.getSelectionModel().select(0);
                cmbMax.getSelectionModel().select(0);
                cmbRegal.getSelectionModel().select(0);
                cmbTablar.getSelectionModel().select(0);

                return;
            }

            ProduktTyp pTyp = wrapper.getProduktTyp();
            Lieferant lieferant = pTyp.getLieferant();

            txtLieferanten.setText(lieferant.getName());
            cmbLieferanten.getSelectionModel().select(cmbLieferanten.getItems().indexOf(lieferant));
            txtProduktTypName.setText(pTyp.getName());
            txtProduktTypCode.setText(pTyp.getTypCode());
            txtPreis.setText("" + pTyp.getPreis());
            txtBeschreibung.setText(pTyp.getBeschreibung());
            cmbMin.getSelectionModel().select(cmbMin.getItems().indexOf(pTyp.getMinimalerBestand()));
            cmbMax.getSelectionModel().select(cmbMax.getItems().indexOf(pTyp.getMaximalerBestand()));

            Tablar tablar = pTyp.getAblageTablar();
            Regal regal = Context.getInstance().getMoebelhausLagerService().regalByTablar(tablar);

            cmbRegal.getSelectionModel().select(cmbRegal.getItems().indexOf(regal));

            /* Tablar neu einlesen */
            if (regal != null) {
                cmbTablar.getItems().addAll(regal.getTablarListe());
            }

            cmbTablar.getSelectionModel().select(tablar);

        } catch (Exception e) {
            logger.error("Fehler beim Updaten der Tabelle: ", e);
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void speichern() {

        if (eingabeValid()) {

            if (tblProduktTyp.getSelectionModel().getSelectedItem() != null) {
                /*
                 * Ein bestehender ProduktTyp soll (evtl. nach Änderungen)
                 * gespeichert werden
                 */
                ProduktTyp produktTyp = tblProduktTyp.getSelectionModel().getSelectedItem().getProduktTyp();

                produktTyp.setLieferant(cmbLieferanten.getSelectionModel().getSelectedItem());
                produktTyp.setName(txtProduktTypName.getText());
                produktTyp.setTypCode(txtProduktTypCode.getText());
                produktTyp.setBeschreibung(txtBeschreibung.getText());
                produktTyp.setPreis(Double.parseDouble(txtPreis.getText()));
                produktTyp.setAblageTablar(cmbTablar.getSelectionModel().getSelectedItem());
                produktTyp.setMinimalerBestand(cmbMin.getSelectionModel().getSelectedItem());
                produktTyp.setMaximalerBestand(cmbMax.getSelectionModel().getSelectedItem());

                try {
                    Context.getInstance().getMoebelhausLagerService().produktTypUpdaten(produktTyp);
                    updateTable();
                    reset();

                    cmbLieferanten.setVisible(false);
                    txtLieferanten.setVisible(true);

                } catch (Exception e) {
                    logger.error("Fehler beim Updaten des ProduktTyps: ", e);
                    lblError.setText(ERROR_MSG_UPDATE_MISSLUNGEN);
                    return;
                }

            } else {

                /* Ein neuer ProduktTyp soll gespeichert werden */
                Lieferant lieferant = cmbLieferanten.getSelectionModel().getSelectedItem();
                String name = txtProduktTypName.getText();
                String typCode = txtProduktTypCode.getText();
                String beschreibung = txtBeschreibung.getText();
                double preis = Double.parseDouble(txtPreis.getText());
                Tablar tablar = cmbTablar.getSelectionModel().getSelectedItem();
                int minBestand = cmbMin.getSelectionModel().getSelectedItem();
                int maxBestand = cmbMax.getSelectionModel().getSelectedItem();

                ProduktTyp produktTyp = new ProduktTyp(name, beschreibung, preis, typCode, lieferant);
                produktTyp.setAblageTablar(tablar);
                produktTyp.setMinimalerBestand(minBestand);
                produktTyp.setMaximalerBestand(maxBestand);

                try {
                    Context.getInstance().getMoebelhausLagerService().produktTypHinzufuegen(produktTyp);
                    updateTable();
                    reset();

                    cmbLieferanten.setVisible(false);
                    txtLieferanten.setVisible(true);

                } catch (Exception e) {
                    logger.error("Fehler beim Sepichern des neuen ProduktTyps: ", e);
                    lblError.setText(ERROR_MSG_SPEICHERN_MISSLUNGEN);
                    return;
                }
            }
        }
    }

    private boolean eingabeValid() {

        if (isValid(txtProduktTypName.getText()) && isValid(txtProduktTypCode.getText()) && isValid(txtPreis.getText())
                && isValid(txtBeschreibung.getText())) {
            /* Prüfen, od der Preis korrekt eingegeben wurde */
            try {
                Double.parseDouble(txtPreis.getText());
                return true;
            } catch (NumberFormatException e) {
                lblError.setText(ERROR_MSG_PREIS_EINGABE_NICHT_KORREKT);
                return false;
            }
        } else {
            lblError.setText(ERROR_MSG_EINGABE_NICHT_KORREKT);
            return false;
        }
    }

    @FXML
    private void printInfoKarte() {

        if (tblProduktTyp.getSelectionModel().getSelectedItem() != null) {

            ProduktTyp produktTyp = tblProduktTyp.getSelectionModel().getSelectedItem().getProduktTyp();

            /* Infokarte als Pdf printen */
            try {
                Context.getInstance().getPdfPrinter().printInfoKarte(produktTyp);

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Infokarte-Generierung");
                alert.setHeaderText("Information");
                alert.setContentText("Die Infokarte wurde erfolgreich generiert.");
                alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
                alert.showAndWait();

            } catch (Exception e) {
                logger.error("Fehler bei der Generierung der Infokarte: ", e);
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void loeschen() {

        /* Löschen */
        try {

            ProduktTyp pTyp = null;
            ProduktTypWrapper wrapper = tblProduktTyp.getSelectionModel().getSelectedItem();

            if (wrapper != null) {
                pTyp = wrapper.getProduktTyp();
            }

            Context.getInstance().getMoebelhausLagerService().produktTypLoeschen(pTyp);

            updateTable();
            reset();

        } catch (Exception e) {
            logger.error("Fehler beim Löschen des ProduktTyp-Objekts: ", e);
            lblError.setText(ERROR_MSG_LOESCHEN_MISSLUNGEN);
            return;
        }
    }

    @FXML
    private void reset() {

        cmbLieferanten.getSelectionModel().select(0);
        txtLieferanten.setText("");
        tblProduktTyp.getSelectionModel().clearSelection();
        txtProduktTypName.setText("");
        txtProduktTypCode.setText("");
        txtPreis.setText("0.0");
        txtBeschreibung.setText("");
        cmbMin.getSelectionModel().select(0);
        cmbMax.getSelectionModel().select(0);
        cmbRegal.getSelectionModel().select(0);
        cmbTablar.getSelectionModel().select(0);
        tblProduktTyp.getSelectionModel().clearSelection();
    }

    @FXML
    private void neuenProduktTypHinzuguegen() {
        reset();
        txtLieferanten.setVisible(false);
        cmbLieferanten.setVisible(true);
        txtProduktTypName.requestFocus();
    }

    private boolean isValid(String str) {
        return str != null && str.trim().length() > 0;
    }
}