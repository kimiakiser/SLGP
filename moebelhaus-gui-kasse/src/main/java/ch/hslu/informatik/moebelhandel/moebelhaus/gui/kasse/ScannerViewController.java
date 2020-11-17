package ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Produkt;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ScannerViewController implements Initializable {

	private static Logger logger = LogManager.getLogger(ScannerViewController.class);

	@FXML
	private Label lblError;

	@FXML
	private ComboBox<String> cmbProduktTypName;

	@FXML
	private ComboBox<String> cmbProduktTypCode;

	@FXML
	private ComboBox<Produkt> cmbProduktCode;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			initComboBoxes();
		} catch (Exception e) {
			logger.error("Fehler beim Initialisieren: ", e);
			throw new RuntimeException(e);
		}

	}

	private void initComboBoxes() throws Exception {

		/* ComboBox cmbProduktTypName initialisieren */
		Map<String, Map<ProduktTyp, List<Produkt>>> map = getAlleProdukteGruppiert();

		TreeSet<String> tsName = new TreeSet<>();

		for (String name : map.keySet()) {
			tsName.add(name);
		}

		ObservableList<String> oListeName = FXCollections.observableArrayList();

		oListeName.addAll(tsName);

		cmbProduktTypName.setItems(oListeName);

		if (oListeName.size() > 0) {
			cmbProduktTypName.getSelectionModel().select(0);
		}

		if (cmbProduktTypName.getSelectionModel().getSelectedItem() != null) {

			/* ComboBox cmbProduktTypCode initialisieren */
			try {

				String produktTypName = cmbProduktTypName.getSelectionModel().getSelectedItem();

				List<ProduktTyp> listeCode = new ArrayList<ProduktTyp>(map.get(produktTypName).keySet());

				TreeSet<ProduktTyp> tsCode = new TreeSet<ProduktTyp>(new Comparator<ProduktTyp>() {

					@Override
					public int compare(ProduktTyp a, ProduktTyp b) {
						return a.getTypCode().compareTo(b.getTypCode());
					}
				});

				tsCode.addAll(listeCode);

				ObservableList<String> oListeCode = FXCollections.observableArrayList();

				for (ProduktTyp typ : tsCode) {
					oListeCode.add(typ.getTypCode());
				}

				cmbProduktTypCode.setItems(oListeCode);

				if (oListeCode.size() > 0) {
					cmbProduktTypCode.getSelectionModel().select(0);
				}

			} catch (Exception e) {
				logger.error("Fehler beim Initialisieren: ", e);
				throw new RuntimeException(e);
			}

			/* ComboBox cmbProduktCode initialisieren */

			String produktTypCode = cmbProduktTypCode.getSelectionModel().getSelectedItem();

			if (produktTypCode != null) {
				ProduktTyp produktTyp = null;

				Set<ProduktTyp> produktTypSet = map.get(cmbProduktTypName.getSelectionModel().getSelectedItem())
						.keySet();

				for (ProduktTyp pTyp : produktTypSet) {
					if (produktTypCode.equals(pTyp.getTypCode())) {
						produktTyp = pTyp;
					}
				}

				List<Produkt> produktListe = map.get(produktTyp.getName()).get(produktTyp);

				TreeSet<Produkt> ts = new TreeSet<>(new Comparator<Produkt>() {

					@Override
					public int compare(Produkt a, Produkt b) {

						if (a.getCode() > b.getCode()) {
							return 1;
						} else if (a.getCode() == b.getCode()) {
							return 0;
						}

						return -1;
					}
				});

				ts.addAll(produktListe);

				ObservableList<Produkt> oListe = FXCollections.observableArrayList();

				oListe.addAll(ts);

				cmbProduktCode.setItems(oListe);

				if (cmbProduktCode.getItems().size() > 0) {
					cmbProduktCode.getSelectionModel().select(0);
				}

			}

		}

		/* cmbProduktTypCode updaten bei einer Auswahl-Änderung */
		cmbProduktTypName.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				lblError.setText("");

				String produktTypName = newValue;

				try {

					/* Alle Code-Werte holen */
					List<ProduktTyp> produktCodeListe = new ArrayList<ProduktTyp>(map.get(produktTypName).keySet());

					/* Sortieren */
					TreeSet<ProduktTyp> ts = new TreeSet<ProduktTyp>(new Comparator<ProduktTyp>() {

						@Override
						public int compare(ProduktTyp a, ProduktTyp b) {
							return a.getTypCode().compareTo(b.getTypCode());
						}
					});

					ts.addAll(produktCodeListe);

					/* Code-Werte in ComboBox ablegen */
					ObservableList<String> produktTypCodeListe = FXCollections.observableArrayList();

					for (ProduktTyp typ : ts) {
						produktTypCodeListe.add(typ.getTypCode());
					}

					cmbProduktTypCode.setItems(produktTypCodeListe);

					if (produktTypCodeListe.size() > 0) {
						cmbProduktTypCode.getSelectionModel().select(0);
					}

				} catch (Exception e) {
					logger.error("Fehler beim Initialisieren: ", e);
					throw new RuntimeException(e);
				}
			}
		});

		/* Anzeige von ProduktCode-Werten im Combo */
		Callback<ListView<Produkt>, ListCell<Produkt>> cellFactory = new Callback<ListView<Produkt>, ListCell<Produkt>>() {

			@Override
			public ListCell<Produkt> call(ListView<Produkt> param) {

				return new ListCell<Produkt>() {

					@Override
					protected void updateItem(Produkt item, boolean empty) {

						super.updateItem(item, empty);

						if (item != null) {
							setText("" + item.getCode());
						}
					}
				};
			}
		};

		cmbProduktCode.setButtonCell(cellFactory.call(null));
		cmbProduktCode.setCellFactory(cellFactory);

		/* Produkt-Code updaten nach jeder Änderung des ProduktTypCodes */
		cmbProduktTypCode.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				try {

					lblError.setText("");

					/*
					 * Alle Produkte holen, welche dem ausgewählten ProduktTyp
					 * entsprechen und deren ProduktCode-Werte in ComboBox
					 * ablegen
					 */
					String produktTypCode = cmbProduktTypCode.getSelectionModel().getSelectedItem();

					if (produktTypCode != null) {
						ProduktTyp produktTyp = null;

						Set<ProduktTyp> produktTypSet = map.get(cmbProduktTypName.getSelectionModel().getSelectedItem())
								.keySet();

						for (ProduktTyp pTyp : produktTypSet) {
							if (produktTypCode.equals(pTyp.getTypCode())) {
								produktTyp = pTyp;
							}
						}

						List<Produkt> produktListe = map.get(produktTyp.getName()).get(produktTyp);

						TreeSet<Produkt> ts = new TreeSet<>(new Comparator<Produkt>() {

							@Override
							public int compare(Produkt a, Produkt b) {

								if (a.getCode() > b.getCode()) {
									return 1;
								} else if (a.getCode() == b.getCode()) {
									return 0;
								}

								return -1;
							}
						});

						ts.addAll(produktListe);

						ObservableList<Produkt> oListe = FXCollections.observableArrayList();

						oListe.addAll(ts);

						cmbProduktCode.setItems(oListe);

						if (cmbProduktCode.getItems().size() > 0) {
							cmbProduktCode.getSelectionModel().select(0);
						}
					}

				} catch (Exception e) {
					logger.error("Fehler beim Initialisieren: ", e);
					throw new RuntimeException(e);
				}
			}
		});
	}

	/**
	 * Helper Methode: Liefert alle am Lager verfügbaren Produkte zurück, wobei
	 * sie nach Produktname und Produktcode gruppiert werden.
	 * 
	 * @return
	 */
	private Map<String, Map<ProduktTyp, List<Produkt>>> getAlleProdukteGruppiert() {

		Map<String, Map<ProduktTyp, List<Produkt>>> mapOuter = new HashMap<>();

		/* Liste für ProduktTyp-Instanzen */
		List<ProduktTyp> produktTypListe = new ArrayList<>();

		try {

			/* Alle Produkte holen */
			List<Produkt> produkteAmLager = Context.getInstance().getKasseService()
					.findAlleAmLagerVerfuegbareProdukte();

			/* Alle ProduktTyp-Instanzen extrachieren */
			TreeSet<ProduktTyp> tsProduktTyp = new TreeSet<>(new Comparator<ProduktTyp>() {

				@Override
				public int compare(ProduktTyp a, ProduktTyp b) {
					return a.getTypCode().compareTo(b.getTypCode());
				}
			});

			for (Produkt p : produkteAmLager) {
				tsProduktTyp.add(p.getTyp());
			}

			produktTypListe.addAll(tsProduktTyp);

			/* Namen extrachieren (ohne Wiederholung) */
			TreeSet<String> tsName = new TreeSet<>();
			for (ProduktTyp produktTyp : produktTypListe) {
				tsName.add(produktTyp.getName());
			}

			for (String produktTypName : tsName) {

				/* Alle ProduktTyp-Objekte mit dem obigen Namen holen */
				List<ProduktTyp> produktTypNachName = new ArrayList<>();

				for (ProduktTyp typ : produktTypListe) {
					if (produktTypName.equals(typ.getName())) {
						produktTypNachName.add(typ);
					}
				}

				/* Innere Mappe */
				Map<ProduktTyp, List<Produkt>> mapInner = new HashMap<>();

				for (ProduktTyp produktTyp : produktTypNachName) {

					/*
					 * Alle am Lager verfügabere Produkte von dem aktuellen Typ
					 * holen
					 */
					List<Produkt> produktListe = new ArrayList<>();

					for (Produkt p : produkteAmLager) {
						if (produktTyp.equals(p.getTyp())) {
							produktListe.add(p);
						}
					}

					/*
					 * ProduktTyp als Schlüssel und produktListe als Wert in die
					 * mappeIn ablegen
					 */
					if (produktListe.size() > 0) {
						mapInner.put(produktTyp, produktListe);
					}
				}

				/*
				 * Innere Mappe in die mapOuter ablegen, wobei als Schlüssel der
				 * 'produktTypName' verwendet wird
				 */
				if (mapInner.size() > 0) {
					mapOuter.put(produktTypName, mapInner);
				}
			}

		} catch (Exception e) {
			logger.error("Fehler beim Holen und Gruppieren von am Lager verfügbaren Produkten!", e);
			throw new RuntimeException(e);
		}

		return mapOuter;
	}

	@FXML
	public void uebernehmen() {

		long produktCode = cmbProduktCode.getSelectionModel().getSelectedItem().getCode();

		TextField txtProduktCode = (TextField) Context.getInstance().getContentMap().get("txtProduktCode");

		if (txtProduktCode != null) {
			txtProduktCode.setText(String.valueOf(produktCode));
		}

		/* TextField 'txtProduktCode' aus dem 'contentMap' entfernen */
		Context.getInstance().getContentMap().remove("txtProduktCode");

		/* Window schliessen */
		Stage stage = (Stage) cmbProduktTypName.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void abbrechen() {

		/* TextField 'txtProduktCode' aus dem 'contentMap' entfernen */
		Context.getInstance().getContentMap().remove("txtProduktCode");

		/* Window schliessen */
		Stage stage = (Stage) cmbProduktTypName.getScene().getWindow();
		stage.close();
	}
}
