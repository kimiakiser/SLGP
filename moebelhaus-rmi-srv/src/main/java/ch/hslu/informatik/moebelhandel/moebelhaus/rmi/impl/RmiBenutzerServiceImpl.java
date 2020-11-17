package ch.hslu.informatik.moebelhandel.moebelhaus.rmi.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import ch.hslu.informatik.moebelhandel.moebelhaus.api.BenutzerService;
import ch.hslu.informatik.moebelhandel.moebelhaus.business.benutzer.BenutzerManager;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.RolleTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.rmi.api.RmiBenutzerService;

public class RmiBenutzerServiceImpl extends UnicastRemoteObject implements RmiBenutzerService {

	private static final long serialVersionUID = -1779150232877211182L;
	
	private BenutzerService benutzerService;
	
	public RmiBenutzerServiceImpl() throws RemoteException {

	}
	
	public BenutzerService getBenutzerService() {

        if (benutzerService == null) {
            benutzerService = new BenutzerManager();
        }

        return benutzerService;
    }
	
	@Override
	public Benutzer benutzerHinzufuegen(Benutzer benutzer) throws Exception {
		return getBenutzerService().benutzerHinzufuegen(benutzer);
	}

	@Override
	public Benutzer benutzerAktualisieren(Benutzer benutzer) throws Exception {
		return getBenutzerService().benutzerAktualisieren(benutzer);
	}

	@Override
	public void benutzerLoeschen(Benutzer benutzer) throws Exception {
		getBenutzerService().benutzerLoeschen(benutzer);
		return;
	}

	@Override
	public Benutzer findByBenutzername(String benutzername) throws Exception {
		return getBenutzerService().findByBenutzername(benutzername);
	}

	@Override
	public List<Benutzer> findByRolleTyp(RolleTyp rolleTyp) throws Exception {
		return getBenutzerService().findByRolleTyp(rolleTyp);
	}

	@Override
	public List<Benutzer> findByNachnameUndVorname(String nachname, String vorname) throws Exception {
		return getBenutzerService().findByNachnameUndVorname(nachname, vorname);
	}

	@Override
	public List<Benutzer> alleBenutzer() throws Exception {
		return getBenutzerService().alleBenutzer();
	}

	@Override
	public List<RolleTyp> alleRollen() throws Exception {
		return getBenutzerService().alleRollen();
	}
}