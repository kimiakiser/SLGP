package ch.hslu.informatik.moebelhandel.moebelhaus.rmi;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.rmi.api.RmiBenutzerService;
import ch.hslu.informatik.moebelhandel.moebelhaus.rmi.api.RmiKasseService;
import ch.hslu.informatik.moebelhandel.moebelhaus.rmi.api.RmiLoginService;
import ch.hslu.informatik.moebelhandel.moebelhaus.rmi.api.RmiMoebelhausLagerService;
import ch.hslu.informatik.moebelhandel.moebelhaus.rmi.impl.RmiBenutzerServiceImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.rmi.impl.RmiKasseServiceImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.rmi.impl.RmiLoginServiceImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.rmi.impl.RmiMoebelhausLagerServiceImpl;

/**
 * RMI Server
 *
 */
public class RmiServer {

	private static Logger logger = LogManager.getLogger(RmiServer.class);

	private static final String PROPERTY_FILE_NAME = "rmi_server.properties";
	private static final String FILIALE_LAGER_RMI_REGISTRY_PORT = "rmi.registry.port";

	public static void main(String[] args) {

		int portNr = 0;

		try {

			Properties props = new Properties();

			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTY_FILE_NAME));

			String strPort = props.getProperty(FILIALE_LAGER_RMI_REGISTRY_PORT);

			try {
				portNr = Integer.parseInt(strPort);
			} catch (NumberFormatException e) {
				String msg = "Die Portnummer-Angabe \'" + strPort + "\' ist nicht korrekt";
				logger.error(msg, e);
				System.out.println("\nFEHLER: " + msg + "\n");

				return;
			}

			Registry reg = LocateRegistry.createRegistry(portNr);

			if (reg != null) {

				/* Login-RemoteObject erstellen und binden */
				RmiLoginService loginServiceRO = new RmiLoginServiceImpl();
				reg.rebind(RmiLoginService.REMOTE_OBJECT_NAME, loginServiceRO);
				logger.info("Remote Object \'" + RmiLoginService.REMOTE_OBJECT_NAME + "\' bound!");
				
				/* Benutzer-RemoteObject erstellen und binden */
				RmiBenutzerService benutzerServiceRO = new RmiBenutzerServiceImpl();
				reg.rebind(RmiBenutzerService.REMOTE_OBJECT_NAME, benutzerServiceRO);
				logger.info("Remote Object \'" + RmiBenutzerService.REMOTE_OBJECT_NAME + "\' bound!");
				
				/* MoebelhausLager-RemoteObject erstellen und binden */
				RmiMoebelhausLagerServiceImpl moebelhausLagerServiceRO = new RmiMoebelhausLagerServiceImpl();
				reg.rebind(RmiMoebelhausLagerService.REMOTE_OBJECT_NAME, moebelhausLagerServiceRO);
				logger.info("Remote Object \'" + RmiMoebelhausLagerService.REMOTE_OBJECT_NAME + "\' bound!");
				
				/* Kasse-RemoteObject erstellen und binden */
				RmiKasseService kasseServiceRO = new RmiKasseServiceImpl();
				reg.rebind(RmiKasseService.REMOTE_OBJECT_NAME, kasseServiceRO);
				logger.info("Remote Object \'" + RmiKasseService.REMOTE_OBJECT_NAME + "\' bound!");

				String ip = InetAddress.getLocalHost().getHostAddress();
				String titel = "RMI Server auf " + ip + ":" + portNr + " ist bereit";

				JOptionPane.showMessageDialog(null,
						"<html>" + "<p style=\"text-align: center;\">M&Ouml;BELHAUS</p>" + "<br>"
								+ "Klicken Sie auf OK um RMI-Server herunterzufahren!" + "<br>" + "</html>",
						titel, JOptionPane.INFORMATION_MESSAGE);

				/* RemoteObjects freigeben (unbind) */
				reg.unbind(RmiLoginService.REMOTE_OBJECT_NAME);
				logger.info("Remote Object \'" + RmiLoginService.REMOTE_OBJECT_NAME + "\' unbound!");
				
				reg.unbind(RmiBenutzerService.REMOTE_OBJECT_NAME);
				logger.info("Remote Object \'" + RmiBenutzerService.REMOTE_OBJECT_NAME + "\' unbound!");
				
				reg.unbind(RmiMoebelhausLagerService.REMOTE_OBJECT_NAME);
				logger.info("Remote Object \'" + RmiMoebelhausLagerService.REMOTE_OBJECT_NAME + "\' unbound!");
				
				reg.unbind(RmiKasseService.REMOTE_OBJECT_NAME);
				logger.info("Remote Object \'" + RmiKasseService.REMOTE_OBJECT_NAME + "\' unbound!");

				/* Prozess beenden */
				System.exit(0);

			} else {

				String msg = "Registry konnte auf der Port \'" + portNr + "\' nicht gestartet  werden!";
				logger.error(msg);
				System.out.println("\nFEHLER: " + msg + "\n");

				return;
			}

		} catch (Exception e) {
			String msg = "RMI Server hat Fehler gemeldet";
			logger.error(msg, e);
			System.exit(-1);
		}

	}
}
