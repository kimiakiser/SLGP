package ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class MenuBarViewController implements Initializable{
	
	private static final String TITEL_RECHNUNG_ERSTELLEN = "Rechnung erstellen";
    private static final String TITEL_RECHNUNGEN = "Rechnung suchen";
    private static final String TITEL_PRODUKT_FINDEN = "Produkt suchen";
    private static final String TITEL_PRODUKT_RUECKNAHME = "Produktrücknahme";
    
	@FXML
	private Label lblBenutzer;

	@FXML
	private Label lblTitel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

        String str = Context.getInstance().getBenutzer().getVorname() + " " + Context.getInstance().getBenutzer().getNachname();
        lblBenutzer.setText("Angemeldet: " + str);
        lblBenutzer.setAlignment(Pos.BASELINE_RIGHT);

        Context.getInstance().setMenuBarViewController(this);
    }
	
	@FXML
    public void abmelden() {

        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/LoginView.fxml"));
            Context.getInstance().getMainRoot().setCenter(root);
            Context.getInstance().getMainRoot().setTop(null);

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
	
    @FXML
    public void home() {
    	
    	lblTitel.setText("");

        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/KasseHomeView.fxml"));
            Context.getInstance().getMainRoot().setCenter(root);

        } catch (IOException e) {
            throw new RuntimeException();
        }  
	}
    
    @FXML
    public void rechnungErstellen() {
    	
    	lblTitel.setText(TITEL_RECHNUNG_ERSTELLEN);

        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/RechnungErstellenView.fxml"));
            Context.getInstance().getMainRoot().setCenter(root);

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    
    @FXML
    public void rechnungen() {
    	
    	lblTitel.setText(TITEL_RECHNUNGEN);

        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/RechnungenView.fxml"));
            Context.getInstance().getMainRoot().setCenter(root);

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    
    @FXML
    public void produktFinden() {
    	
    	lblTitel.setText(TITEL_PRODUKT_FINDEN);

        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/ProduktFindenView.fxml"));
            Context.getInstance().getMainRoot().setCenter(root);

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    
    @FXML
    public void produktRuecknahme() {
    	
    	lblTitel.setText(TITEL_PRODUKT_RUECKNAHME);

        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/ProduktRuecknahmeView.fxml"));
            Context.getInstance().getMainRoot().setCenter(root);

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
