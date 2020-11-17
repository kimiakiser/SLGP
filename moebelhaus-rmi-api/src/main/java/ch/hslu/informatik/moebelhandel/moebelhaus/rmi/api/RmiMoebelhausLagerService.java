package ch.hslu.informatik.moebelhandel.moebelhaus.rmi.api;

import java.rmi.Remote;

import ch.hslu.informatik.moebelhandel.moebelhaus.api.MoebelhausLagerService;

public interface RmiMoebelhausLagerService extends MoebelhausLagerService, Remote {

    /* Name des Remote-Objekts */
    public static final String REMOTE_OBJECT_NAME = "FILIALE_LAGER_SERVICE_RO";

}
