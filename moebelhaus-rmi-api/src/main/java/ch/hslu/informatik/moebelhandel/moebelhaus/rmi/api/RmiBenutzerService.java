package ch.hslu.informatik.moebelhandel.moebelhaus.rmi.api;

import java.rmi.Remote;

import ch.hslu.informatik.moebelhandel.moebelhaus.api.BenutzerService;

public interface RmiBenutzerService extends BenutzerService, Remote {

    /* Name des Remote-Objekts */
    public static final String REMOTE_OBJECT_NAME = "BENUTZER_SERVICE_RO";

}
