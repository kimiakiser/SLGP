package ch.hslu.informatik.moebelhandel.moebelhaus.gui.kasse;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.RolleTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.rmi.api.RmiLoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginViewController implements Initializable {
	
	private static Logger logger = LogManager.getLogger(LoginViewController.class);
	
	public static String LOGIN_ERROR_MESSAGE = "Benutzername oder Kennwort nicht korrekt!";
	public static String ACCESS_DENIED_MESSAGE = "Sie haben keine Zugriffsberechtigung!";
	public static String SERVER_NOT_AVAILABLE = "Die Anmeldung ist zurzeit nicht möglich!";

	@FXML
	private Label lblError;

	@FXML
	private TextField txtBenutzername;

	@FXML
	private TextField txtKennwort;

	@FXML
	private Button button;

	public LoginViewController() {
	}

	@FXML
	private void anmelden(ActionEvent event) {

		lblError.setText("");

		RmiLoginService loginService = null;

		try {
			loginService = Context.getInstance().getLoginService();
		} catch (Exception conException) {
			logger.error(conException.getMessage(), conException);
			lblError.setText(LoginViewController.SERVER_NOT_AVAILABLE);
			return;
		}

		try {
			Benutzer benutzer = loginService.login(txtBenutzername.getText(), txtKennwort.getText());

			if (benutzer != null) {

				if (benutzer.getRolle() == RolleTyp.KASSE_MITARBEITER
						|| benutzer.getRolle() == RolleTyp.FILIALE_LEITER
						|| benutzer.getRolle() == RolleTyp.ADMINISTRATOR) {
					Context.getInstance().setBenutzer(benutzer);

					AnchorPane menuBar = FXMLLoader.load(getClass().getResource("/fxml/MenuBarView.fxml"));
					AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/KasseHomeView.fxml"));
					Context.getInstance().getMainRoot().setTop(menuBar);
					Context.getInstance().getMainRoot().setCenter(root);

				} else {
					lblError.setText(LoginViewController.ACCESS_DENIED_MESSAGE);
				}
			} else {
				lblError.setText(LoginViewController.LOGIN_ERROR_MESSAGE);
			}

		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		txtBenutzername.requestFocus();
	}	
}
