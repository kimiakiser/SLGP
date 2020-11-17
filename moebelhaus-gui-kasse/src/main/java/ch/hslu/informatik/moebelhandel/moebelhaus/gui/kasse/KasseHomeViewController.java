package ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class KasseHomeViewController implements Initializable{
	
	private static Logger logger = LogManager.getLogger(KasseHomeViewController.class);

	private static final String MOEBELHAUS_PROPERTIES_FILE_NAME = "moebelhaus.properties";

	@FXML
	private Label lblWillkommen;

	@FXML
	private Label lblMoebelhausname;

	@FXML
	private Label lblAdresse;
	
	@FXML
	private Button btnHomeView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		/* Properties auslesen */
		try {
			Properties props = new Properties();
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(MOEBELHAUS_PROPERTIES_FILE_NAME);

			if (is != null) {
				props.load(is);
			} else {
				throw new RuntimeException("Property File \'" + MOEBELHAUS_PROPERTIES_FILE_NAME + "\' nicht gefunden!");
			}

			String strWillkommen = props.getProperty("willkommen_meldung", "Willkommen");
			String strName = props.getProperty("moebelhaus_name", " ");
			String strStrasse = props.getProperty("moebelhaus_strasse", " ");
			String strPlz = props.getProperty("moebelhaus_plz", " ");
			String strOrt = props.getProperty("moebelhaus_ort", " ");

			lblWillkommen.setText(strWillkommen);
			lblMoebelhausname.setText(strName);
			lblAdresse.setText(strPlz + " - " + strOrt + ", " + strStrasse);

		} catch (IOException e) {
			logger.error("Fehler beim Holen des 'moebelhaus.properties' Datei:", e);
			throw new RuntimeException(e);
		}
	}
	
	@FXML
    private void rechnungErstellen() {
		Context.getInstance().getMenuBarViewController().rechnungErstellen();
	}
	
    @FXML
    private void rechnungen() {
    	Context.getInstance().getMenuBarViewController().rechnungen();
    }
    
    @FXML
    private void produktFinden() {
    	Context.getInstance().getMenuBarViewController().produktFinden();
    }
    
    @FXML
    private void produktRuecknahme() {
    	Context.getInstance().getMenuBarViewController().produktRuecknahme();
    }
}