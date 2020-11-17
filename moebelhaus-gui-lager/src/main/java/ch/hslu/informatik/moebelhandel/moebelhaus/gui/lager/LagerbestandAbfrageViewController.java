package ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager.wrapper.ProduktTypWrapper;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class LagerbestandAbfrageViewController implements Initializable {

	private static Logger logger = LogManager.getLogger(LagerbestandAbfrageViewController.class);

	@FXML
	private ComboBox<String> cmbProduktTyp;

	@FXML
	private TableView<ProduktTypWrapper> tblAktuellerBestand;
	
	@FXML
	private TableColumn<ProduktTypWrapper, Integer> colNr;
	@FXML
	private TableColumn<ProduktTypWrapper, String> colProduktTypCode;
	@FXML
	private TableColumn<ProduktTypWrapper, String> colBeschreibung;
	@FXML
	private TableColumn<ProduktTypWrapper, String> colLieferant;
	@FXML
	private TableColumn<ProduktTypWrapper, Double> colPreis;
	@FXML
	private TableColumn<ProduktTypWrapper, Integer> colAnzahlInt;
	@FXML
	private TableColumn<ProduktTypWrapper, Integer> colAnzahlExt;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {

			/* cmbProduktTyp initialisieren */
			List<ProduktTyp> alleProduktTypListe = Context.getInstance().getMoebelhausLagerService().produktTypAlle();

			TreeSet<String> typNamen = new TreeSet<>();

			for (ProduktTyp pTyp : alleProduktTypListe) {
				typNamen.add(pTyp.getName());
			}

			ObservableList<String> produktTypListe = FXCollections.observableArrayList();
			produktTypListe.addAll(typNamen);
			cmbProduktTyp.setItems(produktTypListe);

			if (produktTypListe.size() > 0) {
				cmbProduktTyp.getSelectionModel().select(0);
			}

			/* TableView konfigurieren */
			colNr.setCellValueFactory(new PropertyValueFactory<ProduktTypWrapper, Integer>("nr"));
			colProduktTypCode.setCellValueFactory(new PropertyValueFactory<ProduktTypWrapper, String>("produktTypCode"));
			colBeschreibung.setCellValueFactory(new PropertyValueFactory<ProduktTypWrapper, String>("beschreibung"));
			colLieferant.setCellValueFactory(new PropertyValueFactory<ProduktTypWrapper, String>("lieferant"));
			colPreis.setCellValueFactory(new PropertyValueFactory<ProduktTypWrapper, Double>("preis"));
			colAnzahlInt.setCellValueFactory(new PropertyValueFactory<ProduktTypWrapper, Integer>("anzahlInt"));
			colAnzahlExt.setCellValueFactory(new PropertyValueFactory<ProduktTypWrapper, Integer>("anzahlExt"));

			ObservableList<ProduktTypWrapper> bestandListe = FXCollections.observableArrayList();

			tblAktuellerBestand.setItems(bestandListe);

			updateTable();

		} catch (Exception e) {
			logger.error("Fehler bei der Initialisierung der View: ", e);
			throw new RuntimeException(e);
		}

	}

	@FXML
	private void updateTable() {

		try {

			/* cmbProduktTyp initialisieren */
			List<ProduktTyp> alleProduktTypListe = Context.getInstance().getMoebelhausLagerService().produktTypAlle();

			if (cmbProduktTyp.getSelectionModel().getSelectedItem() != null) {

				List<ProduktTyp> tempListe = new ArrayList<>();

				/* Suche alle ProduktTyp-Objekte für den gewählten Namen */
				for (ProduktTyp pTyp : alleProduktTypListe) {

					if (pTyp.getName().equals(cmbProduktTyp.getSelectionModel().getSelectedItem())) {
						tempListe.add(pTyp);
					}
				}

				/*
				 * In 'tempListe' stehen alle PrduktTyp-Objekte, deren Name identisch mit dem
				 * gewählten Namen ist. Für jedes dieser Objekte muss jetzt ein
				 * 'ProduktTypWrapper' Objekt erstellt werden
				 */

				ObservableList<ProduktTypWrapper> bestandListe = FXCollections.observableArrayList();

				for (int i = 0; i < tempListe.size(); i++) {
					int nr = i + 1;
					ProduktTyp pTyp = tempListe.get(i);

					/* Anzahl bestimmen */
					int anzahlInt = Context.getInstance().getMoebelhausLagerService().produktBestand(pTyp.getTypCode()).size();
					int anzahlExt = Context.getInstance().getWebService().produktBestand(pTyp);
				
					bestandListe.add(new ProduktTypWrapper(nr, pTyp, anzahlInt, anzahlExt));
				}

				tblAktuellerBestand.setItems(bestandListe);
			}
		} catch (Exception e) {
			logger.error("Fehler beim Updaten der Tabelle: ", e);
			throw new RuntimeException(e);
		}
	}
}