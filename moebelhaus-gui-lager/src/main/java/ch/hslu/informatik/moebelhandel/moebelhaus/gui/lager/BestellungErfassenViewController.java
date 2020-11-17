package ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager;

import java.net.URL;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager.wrapper.BestellungPositionWrapper;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Bestellung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.BestellungPosition;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lieferant;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

public class BestellungErfassenViewController implements Initializable {

	private static Logger logger = LogManager.getLogger(BestellungErfassenViewController.class);

	@FXML
	private ComboBox<Lieferant> cmbLieferant;

	@FXML
	private ComboBox<ProduktTyp> cmbProduktname;

	@FXML
	private ComboBox<ProduktTyp> cmbProduktCode;

	@FXML
	private ComboBox<String> cmbModus;
	
	@FXML
	private TextField txtAnzahl;
	
	@FXML
	private Button btnLoeschen;

	@FXML
	private Button btnBestellungGenerieren;

	private Lieferant selectedLieferant;

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

			txtAnzahl.setText("0");
			
			/* cmbModus initialisieren */
			cmbModus.getItems().add("Webservice");
			cmbModus.getItems().add("PDF");
			cmbModus.getSelectionModel().select(0);

			/* cmbLieferant initialisieren */
			updateCmbLieferant();

			/* cmbProduktname initialisieren */
			if (cmbLieferant.getSelectionModel().getSelectedItem() != null) {
				updateCmbProduktname();
			}

			/* cmbProduktTypCode initialisieren */
			if (cmbProduktname.getSelectionModel().getSelectedItem() != null) {

				cmbProduktCode.setConverter(new StringConverter<ProduktTyp>() {

					@Override
					public String toString(ProduktTyp produktTyp) {
						if (produktTyp == null) {
							return "";
						} else {
							return produktTyp.getTypCode();
						}
					}

					@Override
					public ProduktTyp fromString(String string) {
						if (string == null || string.trim().length() == 0) {
							return null;
						} else {
							try {
								return Context.getInstance().getMoebelhausLagerService().findeByProduktTypCode(string);
							} catch (Exception e) {
								logger.error("Fehler bei der Konversion von String zu Objekt: ", e);
								throw new RuntimeException(e);
							}
						}
					}

				});

				updateCmbProduktTypCode();

			}

			cmbLieferant.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Lieferant>() {

				@Override
				public void changed(ObservableValue<? extends Lieferant> observable, Lieferant oldValue, Lieferant newValue) {
					if (newValue != null) {
						selectedLieferant = newValue;
						updateCmbProduktname();
						updateCmbProduktTypCode();
					}
				}
			});

			cmbProduktname.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProduktTyp>() {

				@Override
				public void changed(ObservableValue<? extends ProduktTyp> observable, ProduktTyp oldValue, ProduktTyp newValue) {
					if (newValue != null) {
						updateCmbProduktTypCode();
					}
				}

			});

			/* tblBestellung konfigurieren */
			colNummer.setCellValueFactory(new PropertyValueFactory<BestellungPositionWrapper, Integer>("nummer"));
			colLieferant.setCellValueFactory(new PropertyValueFactory<BestellungPositionWrapper, String>("lieferant"));
			colProduktname.setCellValueFactory(new PropertyValueFactory<BestellungPositionWrapper, String>("produktname"));
			colProduktCode.setCellValueFactory(new PropertyValueFactory<BestellungPositionWrapper, String>("produktCode"));
			colAnzahl.setCellValueFactory(new PropertyValueFactory<BestellungPositionWrapper, Integer>("anzahl"));

			btnLoeschen.disableProperty().bind(Bindings.size(tblBestellung.getSelectionModel().getSelectedItems()).isEqualTo(0));

			/*
			 * Lieferant darf nicht geändert werden, wenn mindestens eine Bestllungsposition
			 * in der Tabelle existiert
			 */
			cmbLieferant.disableProperty().bind(Bindings.size(tblBestellung.getItems()).isNotEqualTo(0));

			/*
			 * Bestellung kann generiert werden, wenn mindestens eine Bestellungsposition
			 * enthalten ist
			 */
			btnBestellungGenerieren.disableProperty().bind(Bindings.size(tblBestellung.getItems()).isEqualTo(0));

		} catch (Exception e) {
			logger.error("Fehler bei der Initialisierung der View: ", e);
			throw new RuntimeException(e);
		}
	}

	@FXML
	private void bestellungspositionHinzufuegen() {

		int anzahl = 0;

		try {
			anzahl = Integer.parseInt(txtAnzahl.getText());

			if (anzahl == 0) {
				return;
			}

			/* BestellungPosition erstellen */
			BestellungPosition bPos = new BestellungPosition(cmbProduktCode.getSelectionModel().getSelectedItem(), anzahl);

			/*
			 * Prüfen, ob evtl. ein ProduktTyp bestellt werden soll, für den eine
			 * Bestellungsposition bereits vorhanden ist. Falls ja, wird nur die Anzahl
			 * entsprechend angepasst.
			 */

			int index = -1;

			for (int i = 0; i < tblBestellung.getItems().size(); i++) {
				BestellungPositionWrapper wrapper = tblBestellung.getItems().get(i);
				if (wrapper.getBestellungPosition().getProduktTyp().getTypCode()
						.equals(bPos.getProduktTyp().getTypCode())) {
					index = i;
					break;
				}
			}

			if (index != -1) {
				/*
				 * Eine BestellungPosition für den ausgewählten ProduktTypCode existiert bereits
				 * und wird mit der zu erstellenden BestellungPosition gemergt.
				 */

				/* Anzahl berechnen (addieren) */
				int anzahlNeu = bPos.getAnzahl() + tblBestellung.getItems().get(index).getBestellungPosition().getAnzahl();
				/*
				 * Die neue Anzahl in die neue BestellungPosition (bPos) übernehemen
				 */
				bPos.setAnzahl(anzahlNeu);

				/*
				 * Neue BestellungPositionWrapper Instanz erstellen, in der bPos enthalten sein
				 * wird
				 */
				BestellungPositionWrapper bPosWrapper = new BestellungPositionWrapper(tblBestellung.getItems().size(), bPos);

				/* Bestehende BestellungPosition entfernen */
				tblBestellung.getItems().remove(index);

				/* Neue, gemergte BestellungPosition einfügen */
				tblBestellung.getItems().add(index, bPosWrapper);

			} else {
				BestellungPositionWrapper bPosWrapper = new BestellungPositionWrapper(tblBestellung.getItems().size() + 1, bPos);
				tblBestellung.getItems().add(bPosWrapper);
			}

			txtAnzahl.setText("0");

		} catch (NumberFormatException e) {
			
			/* Alert-Dialog anzeigen */
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Alert Lager");
			alert.setHeaderText("Information");
			alert.setContentText("Die Eingabe für Anzahl ist nicht korrekt.");
			alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
			alert.showAndWait();
			return;
		}
	}

	@FXML
	private void bestellungPositionLoeschen() {

		BestellungPositionWrapper bPosWrapper = tblBestellung.getSelectionModel().getSelectedItem();

		if (bPosWrapper != null) {
			tblBestellung.getItems().remove(bPosWrapper);
			tblBestellung.getSelectionModel().clearSelection();
		}
	}

	@FXML
	private void abbrechen() {

		tblBestellung.getItems().clear();

		if (cmbLieferant.getItems().size() > 0) {
			cmbLieferant.getSelectionModel().select(0);
		} else {
			if (cmbProduktname.getItems().size() > 0) {
				cmbProduktname.getSelectionModel().select(0);
			} else {
				if (cmbProduktCode.getItems().size() > 0) {
					cmbProduktCode.getSelectionModel().select(0);
				}
			}
		}

		txtAnzahl.setText("0");
	}

	@FXML
	private void bestellungGenerieren() {

		try {
			Bestellung bestellung = new Bestellung(new GregorianCalendar(), Context.getInstance().getMoebelhaus());

			for (BestellungPositionWrapper wrapper : tblBestellung.getItems()) {
				bestellung.getBestellungPositionListe().add(wrapper.getBestellungPosition());
			}
				
			if (cmbModus.getSelectionModel().getSelectedItem().equals("Webservice")) {
				
				/* Bestellung generieren */
				Context.getInstance().getWebService().bestellen(bestellung, Context.getInstance().getMoebelhaus().getMoebelhausCode());
				
				/* Bestellung speichern */
				bestellung = Context.getInstance().getMoebelhausLagerService().bestellen(bestellung);
				
			} else if (cmbModus.getSelectionModel().getSelectedItem().equals("PDF")) {
				
				/* Bestellung speichern */
				bestellung = Context.getInstance().getMoebelhausLagerService().bestellen(bestellung);
				
				/* Bestellung als PDF generieren */
				Context.getInstance().getPdfPrinter().printBestellungAlsPdf(bestellung);
			}

			/* Info-Dialog anzeigen */
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Alert Lager");
			alert.setHeaderText("Information");
			alert.setContentText("Die Bestellung wurde erfolgreich generiert.");
			alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
			alert.showAndWait();

			tblBestellung.getItems().clear();
			abbrechen();

		} catch (Exception e) {
			logger.error("Fehler bei der Generierung der Bestellung: ", e);

			/* Alert-Dialog anzeigen */
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Alert Lager");
			alert.setHeaderText("Information");
			alert.setContentText("Das Generieren der Bestellung ist misslungen.");
			alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
			alert.showAndWait();
		}
	}

	private void updateCmbLieferant() {

		try {

			List<Lieferant> lieferantListe = Context.getInstance().getMoebelhausLagerService().alleLieferanten();
			cmbLieferant.getItems().clear();
			cmbLieferant.getItems().addAll(lieferantListe);

			if (selectedLieferant == null) {
				if (cmbLieferant.getItems().size() > 0) {
					cmbLieferant.getSelectionModel().select(0);
					selectedLieferant = cmbLieferant.getSelectionModel().getSelectedItem();
				}
			}

		} catch (Exception e) {
			logger.error("Fehler bei der Aktualisierung von cmbLieferant: ", e);
			throw new RuntimeException(e);
		}

	}

	private void updateCmbProduktname() {

		Lieferant lieferant = cmbLieferant.getSelectionModel().getSelectedItem();

		if (lieferant != null) {

			try {
				List<ProduktTyp> produktTypListe = Context.getInstance().getMoebelhausLagerService().findeByLieferant(lieferant);

				TreeSet<ProduktTyp> ts = new TreeSet<>(new Comparator<ProduktTyp>() {

					@Override
					public int compare(ProduktTyp a, ProduktTyp b) {
						return a.getName().compareTo(b.getName());
					}
				});

				ts.addAll(produktTypListe);

				cmbProduktname.getItems().clear();
				cmbProduktname.getItems().addAll(ts);

				if (cmbProduktname.getItems().size() > 0) {
					cmbProduktname.getSelectionModel().select(0);

				}

			} catch (Exception e) {
				logger.error("Fehler bei der Aktualisierung von cmbProduktname: ", e);
				throw new RuntimeException(e);
			}
		}

	}

	private void updateCmbProduktTypCode() {

		try {

			List<ProduktTyp> produktTypListe = Context.getInstance().getMoebelhausLagerService().findeByProduktname(cmbProduktname.getSelectionModel().getSelectedItem().getName());

			/*
			 * ProduktTyp-Instanzen entfernen, die nicht von dem ausgewählten Lieferanten
			 * sind
			 */
			for (Iterator<ProduktTyp> it = produktTypListe.iterator(); it.hasNext();) {
				ProduktTyp pTyp = it.next();

				if (pTyp.getLieferant().getId() != selectedLieferant.getId()) {
					it.remove();
				}
			}

			cmbProduktCode.getItems().clear();
			cmbProduktCode.getItems().addAll(produktTypListe);

			if (cmbProduktCode.getItems().size() > 0) {
				cmbProduktCode.getSelectionModel().select(0);
			}

		} catch (Exception e) {
			logger.error("Fehler bei der Aktualisierung von cmbProduktCode: ", e);
			throw new RuntimeException(e);
		}
	}
}