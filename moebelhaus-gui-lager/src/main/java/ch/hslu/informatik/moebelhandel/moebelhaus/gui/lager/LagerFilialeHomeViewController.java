package ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager;

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

public class LagerFilialeHomeViewController implements Initializable {

	private static Logger logger = LogManager.getLogger(LagerFilialeHomeViewController.class);

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
			String msg = "Fehler beim Holen des 'moebelhaus.properties' Datei:";
			logger.error(msg, e);
			throw new RuntimeException(msg);
		}
	}
	
	@FXML
    private void lagerbestand() {
		Context.getInstance().getMenuBarViewController().lagerbestandAbfragen();
	}
	
    @FXML
    private void bestellungErfassen() {
    	Context.getInstance().getMenuBarViewController().bestellungErfassen();
    }
    
    @FXML
    private void lieferungAnnehmen() {
    	Context.getInstance().getMenuBarViewController().lieferungAnnehmen();
    }
    
    @FXML
    private void produktZuruecksenden() {
    	Context.getInstance().getMenuBarViewController().produktZuruecksenden();
    }
}