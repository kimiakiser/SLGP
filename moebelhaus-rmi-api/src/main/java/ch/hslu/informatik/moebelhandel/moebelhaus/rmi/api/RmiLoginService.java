package ch.hslu.informatik.moebelhandel.moebelhaus.rmi.api;

import java.rmi.Remote;

import ch.hslu.informatik.moebelhandel.moebelhaus.api.LoginService;

public interface RmiLoginService extends LoginService, Remote {

    /* Name des Remote-Objekts */
    public static final String REMOTE_OBJECT_NAME = "LOGIN_SERVICE_RO";

}
