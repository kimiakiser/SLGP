package ch.hslu.informatik.moebelhandel.moebelhaus.rmi.api;

import java.rmi.Remote;

import ch.hslu.informatik.moebelhandel.moebelhaus.api.KasseService;;

public interface RmiKasseService extends KasseService, Remote {

    /* Name des Remote-Objekts */
    public static final String REMOTE_OBJECT_NAME = "KASSE_SERVICE_RO";

}
