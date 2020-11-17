package ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse;

import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse.wapper.ProduktTypWrapper;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Produkt;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProduktFindenViewController implements Initializable {

	private static Logger logger = LogManager.getLogger(ProduktFindenViewController.class);

	@FXML
	private ComboBox<String> cmbProduktTyp;
	
	@FXML
	private TextField txtPreisMin;
	
	@FXML
	private TextField txtPreisMax;
	
	@FXML
	private Button button;
	
	@FXML
	private TableView<ProduktTypWrapper> tblSuchergebnis;
	@FXML
	private TableColumn<ProduktTypWrapper, String> colProdukteName;	
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
			/* TableView konfigurieren */
			colProdukteName.setCellValueFactory(new PropertyValueFactory<ProduktTypWrapper, String>("produkteName"));
			colProduktTypCode.setCellValueFactory(new PropertyValueFactory<ProduktTypWrapper, String>("produktTypCode"));
			colBeschreibung.setCellValueFactory(new PropertyValueFactory<ProduktTypWrapper, String>("beschreibung"));
			colLieferant.setCellValueFactory(new PropertyValueFactory<ProduktTypWrapper, String>("lieferant"));
			colPreis.setCellValueFactory(new PropertyValueFactory<ProduktTypWrapper, Double>("preis"));
			colAnzahlInt.setCellValueFactory(new PropertyValueFactory<ProduktTypWrapper, Integer>("anzahlInt"));
			colAnzahlExt.setCellValueFactory(new PropertyValueFactory<ProduktTypWrapper, Integer>("anzahlExt"));
			
			ObservableList<ProduktTypWrapper> lagerListe = FXCollections.observableArrayList();
			tblSuchergebnis.setItems(lagerListe);
			
			initComboBoxes();
			tabelleFüllen();
			addAnzahlExt();
			
		} catch (Exception e) {
			logger.error("Fehler bei der Initialisierung der View: ", e);
			throw new RuntimeException(e);
		}
	}
	
	private void tabelleFüllen() {
		try {
			tblSuchergebnis.getItems().clear();

			/* Alle Produkte holen */
			List<Produkt> produkteListe = Context.getInstance().getKasseService().findAlleAmLagerVerfuegbareProdukte();

			for(Produkt produkt : produkteListe) {
				ProduktTyp produktTyp = produkt.getTyp();
				
				ProduktTypWrapper produktTypWrapper = new ProduktTypWrapper(produktTyp, 1, 0);
				
				int index = -1;
				
				for(int i = 0; i < tblSuchergebnis.getItems().size(); i++) {
					ProduktTypWrapper typWrapper = tblSuchergebnis.getItems().get(i);
					if (produktTypWrapper.getProduktTypCode().equals(typWrapper.getProduktTypCode())) {
						index = i;
						break;
					}
				}

				if (index != -1) {
					int anzahlNeu = 1 + tblSuchergebnis.getItems().get(index).getAnzahlInt();
					produktTypWrapper.setAnzahlInt(anzahlNeu);
					tblSuchergebnis.getItems().remove(index);
					tblSuchergebnis.getItems().add(index, produktTypWrapper);
				} else {
					tblSuchergebnis.getItems().add(produktTypWrapper);
				}
			}
			
			addAnzahlExt();	

		} catch (Exception e) {
			logger.error("Fehler beim Befüllen der Tabelle.", e);
			throw new RuntimeException(e);
		}
	}

	@FXML
	private void initComboBoxes() throws Exception {
		
		/* ComboBox initialisieren */
		Set<String> tsName = new HashSet<>();
		ObservableList<String> oListeName = FXCollections.observableArrayList();
		oListeName.add("Alle Kategorien");
		List<Produkt> produkteListe = Context.getInstance().getKasseService().findAlleAmLagerVerfuegbareProdukte();

		for(Produkt produkt : produkteListe) {
			 tsName.add(produkt.getTyp().getName());
		}
		
		oListeName.addAll(tsName);
		cmbProduktTyp.setItems(oListeName);
		
		if (oListeName.size() > 0) {
			cmbProduktTyp.getSelectionModel().select(0);
		}
	}
	
	@FXML
	private void suchen() {
		try {
			/* Tabelle neu füllen */
			tabelleFüllen();
			
			/* Nach Produkttyp filtern */
			String typCom = cmbProduktTyp.getSelectionModel().getSelectedItem();
			if (typCom != "Alle Kategorien") {
				for(int i = 0; i < tblSuchergebnis.getItems().size(); i++) {
					String typ = tblSuchergebnis.getItems().get(i).getProduktTyp().getName();
					if (typCom.equals(typ)) {
					} else {
						tblSuchergebnis.getItems().remove(i);
						i--;
					}
				}
			}
			
			/* Nach Preisbereich filtern */
			double min = Double.parseDouble(txtPreisMin.getText());
			double max = Double.parseDouble(txtPreisMax.getText());
			if (min >= 0 && max > min && max >= 0) {
				for(int i = 0; i < tblSuchergebnis.getItems().size(); i++) {
					double preis = tblSuchergebnis.getItems().get(i).getProduktTyp().getPreis();
					if (preis < min || preis > max) {
						tblSuchergebnis.getItems().remove(i);
						i--;
					}
				}
			}
			
			if (tblSuchergebnis.getItems().size() == 0) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Alert Kasse");
				alert.setHeaderText("Information");
				alert.setContentText("Keine Produkte gefunden.");
				alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
				alert.showAndWait();
			}
			
			addAnzahlExt();	

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Alert Kasse");
			alert.setHeaderText("Information");
			alert.setContentText("Bitte gültigen Preisbereich eingeben.");
			alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
			alert.showAndWait();
		}
	}
	
	private void addAnzahlExt() {
		Runnable run = new Runnable() {
			public void run() {
				try {
		        	for(int i = 0; i < tblSuchergebnis.getItems().size(); i++) {
					tblSuchergebnis.getItems().get(i).setAnzahlExt(Context.getInstance().getWebService().produktBestand(tblSuchergebnis.getItems().get(i).getProduktTyp()));
					tblSuchergebnis.refresh();
		        	}
				} catch (Exception e) {	
				}	
			}
		};
		new Thread(run).start();
	}
	
	@FXML
	private void abbrechen() {
		try {
			txtPreisMin.setText("0.00");
			txtPreisMax.setText("0.00");
			cmbProduktTyp.getSelectionModel().select(0);
			tabelleFüllen();
			addAnzahlExt();
			
		} catch (Exception e) {
			logger.error("Fehler beim Abbrechen.", e);
		}
	}
}