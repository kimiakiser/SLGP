package ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager.wrapper.BestellungPositionWrapper;
import ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager.wrapper.BestellungWrapper;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Bestellung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.BestellungPosition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class BestellungenViewController implements Initializable {

    private static Logger logger = LogManager.getLogger(BestellungenViewController.class);
    
    @FXML
    private Label label;
    
    @FXML
    private Button button;
    
    @FXML
    private TableView<BestellungWrapper> tblBestellungen;
    @FXML
    private TableColumn<BestellungWrapper, Integer> colNr;
    @FXML
    private TableColumn<BestellungWrapper, Long> colId;
    @FXML
    private TableColumn<BestellungWrapper, String> colBesteller;
    @FXML
    private TableColumn<BestellungWrapper, String> colLieferantBestellung;
    @FXML
    private TableColumn<BestellungWrapper, String> colDatum;
    
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
        	
            /* tblBestellungen konfigurieren */
            colNr.setCellValueFactory(new PropertyValueFactory<BestellungWrapper, Integer>("nummer"));
            colId.setCellValueFactory(new PropertyValueFactory<BestellungWrapper, Long>("id"));
            colBesteller.setCellValueFactory(new PropertyValueFactory<BestellungWrapper, String>("besteller"));
            colLieferantBestellung.setCellValueFactory(new PropertyValueFactory<BestellungWrapper, String>("lieferantBestellung"));
            colDatum.setCellValueFactory(new PropertyValueFactory<BestellungWrapper, String>("datum"));
            
			ObservableList<BestellungWrapper> bestellungList = FXCollections.observableArrayList();
			tblBestellungen.setItems(bestellungList);
			
			/* tblBestellungen füllen */
			alleBestellungen();
			
            /* tblBestellung konfigurieren */
            colNummer.setCellValueFactory(new PropertyValueFactory<BestellungPositionWrapper, Integer>("nummer"));
            colLieferant.setCellValueFactory(new PropertyValueFactory<BestellungPositionWrapper, String>("lieferant"));
            colProduktname.setCellValueFactory(new PropertyValueFactory<BestellungPositionWrapper, String>("produktname"));
            colProduktCode.setCellValueFactory(new PropertyValueFactory<BestellungPositionWrapper, String>("produktCode"));
            colAnzahl.setCellValueFactory(new PropertyValueFactory<BestellungPositionWrapper, Integer>("anzahl"));
            
            tblBestellungen.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BestellungWrapper>() {

                @Override
                public void changed(ObservableValue<? extends BestellungWrapper> observable, BestellungWrapper oldValue, BestellungWrapper newValue) {

                    if (newValue != null) {
                    	updateTableBestellung();
                    }
                }
            });
            
        } catch (Exception e) {
            logger.error("Fehler bei der Initialisierung der View: ", e);
            throw new RuntimeException(e);
        }
    }
    
    @FXML
    private void alleBestellungen() {
    	try { 
    		List<Bestellung> bestellungL = Context.getInstance().getMoebelhausLagerService().alleBestellungen();
    		
    		tblBestellungen.getItems().clear();
    		
			for (Bestellung bestellung : bestellungL) {
				tblBestellungen.getItems().add(new BestellungWrapper(tblBestellungen.getItems().size() + 1, bestellung));
			}

    		tblBestellung.getItems().clear();
    		
        } catch (Exception e) {
            logger.error("Fehler bei der Laden der Bestellungen aus der Datenbank: ", e);
            throw new RuntimeException(e);
        }
    }
    
    @FXML
    private void offeneBestellungen() {
    	try {
    		List<Bestellung> bestellungL = Context.getInstance().getWebService().offeneBestellungen(Context.getInstance().getMoebelhaus());
    		
    		tblBestellungen.getItems().clear();
    		
			for (Bestellung bestellung : bestellungL) {
				tblBestellungen.getItems().add(new BestellungWrapper(tblBestellungen.getItems().size() + 1, bestellung));
			}
			
    		tblBestellung.getItems().clear();
    		
        } catch (Exception e) {
            logger.error("Fehler bei der Laden der Bestellungen vom Webservice: ", e);
            throw new RuntimeException(e);
        }
    }
    
    private void updateTableBestellung() {

        Bestellung bestellung = tblBestellungen.getSelectionModel().getSelectedItem().getBestellung();

        if (bestellung != null) {

            List<BestellungPosition> liste = bestellung.getBestellungPositionListe();
            List<BestellungPositionWrapper> wrapperListe = new ArrayList<>();

            int nummer = 1;

            for (BestellungPosition bPos : liste) {
                wrapperListe.add(new BestellungPositionWrapper(nummer++, bPos));
            }

            tblBestellung.getItems().clear();
            tblBestellung.getItems().addAll(wrapperListe);
        }
    }
}