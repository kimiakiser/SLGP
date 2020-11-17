package ch.hslu.informatik.moebelhandel.moebelhaus.rmi.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ch.hslu.informatik.moebelhandel.moebelhaus.api.LoginService;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;
import ch.hslu.informatik.moebelhandel.moebelhaus.rmi.api.RmiLoginService;
import ch.hslu.informatik.moebelhandel.verteiler.business.login.LoginManager;

public class RmiLoginServiceImpl extends UnicastRemoteObject implements RmiLoginService {

	private static final long serialVersionUID = 4789219892509583717L;
	
	private LoginService loginService;

    public RmiLoginServiceImpl() throws RemoteException {

    }

    public LoginService getLoginService() {

        if (loginService == null) {
            loginService = new LoginManager();
        }

        return loginService;
    }

    public Benutzer login(String benutzername, String kennwort) throws Exception {
        return getLoginService().login(benutzername, kennwort);
    }

    public boolean kennwortAendern(String benutzername, String kennwortAktuell, String kennwortNeu) throws Exception {
        return getLoginService().kennwortAendern(benutzername, kennwortAktuell, kennwortNeu);
    }
}
