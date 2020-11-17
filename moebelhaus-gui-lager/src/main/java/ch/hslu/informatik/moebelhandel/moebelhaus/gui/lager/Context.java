package ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.api.WebService;
import ch.hslu.informatik.moebelhandel.moebelhaus.business.webservice.WebServiceManager;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Moebelhaus;
import ch.hslu.informatik.moebelhandel.moebelhaus.pdfprinter.PdfPrinter;
import ch.hslu.informatik.moebelhandel.moebelhaus.rmi.api.RmiBenutzerService;
import ch.hslu.informatik.moebelhandel.moebelhaus.rmi.api.RmiLoginService;
import ch.hslu.informatik.moebelhandel.moebelhaus.rmi.api.RmiMoebelhausLagerService;
import javafx.scene.layout.BorderPane;

public class Context {

	private static Logger logger = LogManager.getLogger(Context.class);

	private static final String PROPERTY_FILE_NAME = "rmi_client.properties";
	private static final String POLICY_FILE_NAME = "rmi_client.policy";

	private static Context INSTANCE = new Context();

	private BorderPane mainRoot;

	private Benutzer benutzer;

	private RmiMoebelhausLagerService moebelhausLagerService;

	private RmiBenutzerService benutzerService;

	private RmiLoginService loginService;
	
	private WebService webService;
	
	private PdfPrinter pdfPrinter;

	private Moebelhaus moebelhaus;

	private MenuBarViewController menuBarViewController;

	private Context() {

	}

	public static Context getInstance() {
		return INSTANCE;
	}

	public BorderPane getMainRoot() {
		return mainRoot;
	}

	public void setMainRoot(BorderPane mainRoot) {
		this.mainRoot = mainRoot;
	}

	public Benutzer getBenutzer() {
		return benutzer;
	}

	public void setBenutzer(Benutzer benutzer) {
		this.benutzer = benutzer;
	}

	public RmiMoebelhausLagerService getMoebelhausLagerService() {
		
		int portNr = 0;

		if (moebelhausLagerService == null) {

			try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME)) {

				setSecurityManager();

				Properties props = new Properties();

				if (is == null) {
					throw new RuntimeException("Die Property-Datei \'" + PROPERTY_FILE_NAME + "\' konnte nicht gefunden werden!");
				} else {

					props.load(is);

					String ip = props.getProperty("rmi.server.ip");
					String strPort = props.getProperty("rmi.registry.port");

					try {
						portNr = Integer.parseInt(strPort);
						Registry reg = LocateRegistry.getRegistry(ip, portNr);

						if (reg != null) {
							String url = "rmi://" + ip + ":" + portNr + "/" + RmiMoebelhausLagerService.REMOTE_OBJECT_NAME;
							moebelhausLagerService = (RmiMoebelhausLagerService) Naming.lookup(url);

						} else {
							String msg = "Die Reference auf RMI-Registry konnte auf " + ip + ":" + portNr + " nicht geholt werden!";
							logger.error(msg);
							throw new RuntimeException(msg);
						}

					} catch (NumberFormatException nfe) {
						String msg = "Die Portnummer-Angabe \'" + strPort + "\' ist nicht korrekt";
						logger.error(msg, nfe);
						throw new RuntimeException(nfe);
					}
				}
			} catch (Exception e) {
				String msg = "Fehler beim Holen des RmiMoebelhausLagerRO:";
				logger.error(msg, e);
				throw new RuntimeException(msg);
			}
		}
		return moebelhausLagerService;
	}

	public RmiBenutzerService getBenutzerService() {
		
		int portNr = 0;

		if (benutzerService == null) {

			try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME)) {

				setSecurityManager();

				Properties props = new Properties();

				if (is == null) {
					throw new RuntimeException("Die Property-Datei \'" + PROPERTY_FILE_NAME + "\' konnte nicht gefunden werden!");
				} else {

					props.load(is);

					String ip = props.getProperty("rmi.server.ip");
					String strPort = props.getProperty("rmi.registry.port");

					try {
						portNr = Integer.parseInt(strPort);
						Registry reg = LocateRegistry.getRegistry(ip, portNr);

						if (reg != null) {
							String url = "rmi://" + ip + ":" + portNr + "/" + RmiBenutzerService.REMOTE_OBJECT_NAME;
							benutzerService = (RmiBenutzerService) Naming.lookup(url);

						} else {
							String msg = "Die Reference auf RMI-Registry konnte auf " + ip + ":" + portNr + " nicht geholt werden!";
							logger.error(msg);
							throw new RuntimeException(msg);
						}

					} catch (NumberFormatException nfe) {
						String msg = "Die Portnummer-Angabe \'" + strPort + "\' ist nicht korrekt";
						logger.error(msg, nfe);
						throw new RuntimeException(nfe);
					}
				}
			} catch (Exception e) {
				String msg = "Fehler beim Holen des RmiBenutzerRO:";
				logger.error(msg, e);
				throw new RuntimeException(msg);
			}
		}

		return benutzerService;
	}

	public RmiLoginService getLoginService() {

		int portNr = 0;

		if (loginService == null) {

			try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME)) {

				setSecurityManager();

				Properties props = new Properties();

				if (is == null) {
					throw new RuntimeException("Die Property-Datei \'" + PROPERTY_FILE_NAME + "\' konnte nicht gefunden werden!");
				} else {

					props.load(is);

					String ip = props.getProperty("rmi.server.ip");
					String strPort = props.getProperty("rmi.registry.port");

					try {
						portNr = Integer.parseInt(strPort);
						Registry reg = LocateRegistry.getRegistry(ip, portNr);

						if (reg != null) {
							String url = "rmi://" + ip + ":" + portNr + "/" + RmiLoginService.REMOTE_OBJECT_NAME;
							loginService = (RmiLoginService) Naming.lookup(url);

						} else {
							String msg = "Die Reference auf RMI-Registry konnte auf " + ip + ":" + portNr + " nicht geholt werden!";
							logger.error(msg);
							throw new RuntimeException(msg);
						}

					} catch (NumberFormatException nfe) {
						String msg = "Die Portnummer-Angabe \'" + strPort + "\' ist nicht korrekt";
						logger.error(msg, nfe);
						throw new RuntimeException(nfe);
					}
				}
			} catch (Exception e) {
				String msg = "Fehler beim Holen des RmiLoginRO:";
				logger.error(msg, e);
				throw new RuntimeException(msg);
			}
		}

		return loginService;
	}
	
	public WebService getWebService() {
		
		if (webService == null) {
			webService = new WebServiceManager();
		}
			
		return webService;
	}

	public PdfPrinter getPdfPrinter() {

		try {

			/* PdfPrinter-Klasse auslesen */
			Properties props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTY_FILE_NAME));
			String className = props.getProperty("pdf.printer.class");

			if (pdfPrinter == null) {

				/* Klasse laden */
				Class<?> clazz = Class.forName(className);

				pdfPrinter = (PdfPrinter) clazz.newInstance();
				pdfPrinter = new PdfPrinter();
			}

		} catch (IOException e) {
			String msg = "Fehler beim Auslesen des Namen der PdfPrinter-Klasse:";
			logger.error(msg, e);
			throw new RuntimeException(msg);
		} catch (ClassNotFoundException e) {
			String msg = "PdfPrinter-Klasse konnte nicht gefunden werden:";
			logger.error(msg, e);
			throw new RuntimeException(msg);
		} catch (Exception e) {
			String msg = "PdfPrinter-Klasse konnte nicht instantiiert werden:";
			logger.error(msg, e);
			throw new RuntimeException(msg);
		}

		return pdfPrinter;
	}

	public Moebelhaus getMoebelhaus() {
		if (moebelhaus == null) {
			synchronized (Context.class) {
				try {
					moebelhaus = moebelhausLagerService.getMoebelhaus();
				} catch (Exception e) {
					logger.error("Fehler beim Einlesen des Möbelhauses: ", e);
					throw new RuntimeException(e);
				}
			}
		}
		return moebelhaus;
	}

	public MenuBarViewController getMenuBarViewController() {
		return menuBarViewController;
	}

	public void setMenuBarViewController(MenuBarViewController menuBarViewController) {
		this.menuBarViewController = menuBarViewController;
	}

	/* Diese Methode setzt den SecurityManager */
	private void setSecurityManager() throws IOException {

		/*
		 * Hinweis: die rmi-policy File ist entweder im Verzeichnis 'src' oder in
		 * 'resources', wenn man mit maven arbeitet
		 */

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(POLICY_FILE_NAME);

		File tempFile = File.createTempFile(System.getProperty("user.home") + File.separator + "moebelhandel_rmi_policy", "tmp");

		FileOutputStream fos = new FileOutputStream(tempFile);

		/* Inhalt der policy-Datei in 'tempFile' kopieren */
		int n = 0;

		while ((n = is.read()) != -1) {
			fos.write(n);
		}

		is.close();
		fos.close();

		String pathToTempPolicyFile = tempFile.getAbsolutePath();

		tempFile.deleteOnExit();

		if (System.getSecurityManager() == null) {
			/* Policy-File muss im ROOT-Verzeichnis des Projekts sein! */
			System.setProperty("java.security.policy", pathToTempPolicyFile);
			System.setSecurityManager(new SecurityManager());
		}

	}
}
