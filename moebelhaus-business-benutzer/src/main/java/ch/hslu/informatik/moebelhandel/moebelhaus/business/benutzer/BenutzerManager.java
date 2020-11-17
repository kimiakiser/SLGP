package ch.hslu.informatik.moebelhandel.moebelhaus.business.benutzer;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.api.BenutzerService;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.RolleTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.BenutzerDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.BenutzerDAOImpl;

public class BenutzerManager implements BenutzerService {

    private static Logger logger = LogManager.getLogger(BenutzerManager.class);

    private BenutzerDAO benutzerDAO;

    public BenutzerDAO getBenutzerDAO() {

        if (benutzerDAO == null) {
            benutzerDAO = new BenutzerDAOImpl();
        }

        return benutzerDAO;
    }

    public Benutzer benutzerHinzufuegen(Benutzer benutzer) throws Exception {
        try {
            return getBenutzerDAO().save(benutzer);
        } catch (Exception e) {
            String msg = "Benutzer \'" + benutzer.getNachname() + " " + benutzer.getVorname()
                    + "\' konnte nicht hinzugefügt werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

    public Benutzer benutzerAktualisieren(Benutzer benutzer) throws Exception {
        try {
            return getBenutzerDAO().update(benutzer);
        } catch (Exception e) {
            String msg = "Benutzer \'" + benutzer.getNachname() + " " + benutzer.getVorname()
                    + "\' konnte nicht aktualisiert werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

    public void benutzerLoeschen(Benutzer benutzer) throws Exception {
        try {
            getBenutzerDAO().delete(benutzer);
        } catch (Exception e) {
            String msg = "Benutzer \'" + benutzer.getNachname() + " " + benutzer.getVorname()
                    + "\' konnte nicht gelöscht werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

    public Benutzer findByBenutzername(String benutzername) throws Exception {
        try {
            return getBenutzerDAO().findByBenutzername(benutzername);
        } catch (Exception e) {
            String msg = "Benutzer \'" + benutzername + "\' konnte nicht gefunden werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

    public List<Benutzer> findByRolleTyp(RolleTyp rolleTyp) throws Exception {
        try {
            return getBenutzerDAO().findByRolleTyp(rolleTyp);
        } catch (Exception e) {
            String msg = "Benutzer als \'" + rolleTyp.bezeichnung() + "\' konnten nicht gefunden werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

    public List<Benutzer> findByNachnameUndVorname(String nachname, String vorname) throws Exception {
        try {
            return getBenutzerDAO().findByNachnameUndVorname(nachname, vorname);
        } catch (Exception e) {
            String msg = "Benutzer \'" + nachname + " " + vorname + "\' konnte nicht gefunden werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

    public List<Benutzer> alleBenutzer() throws Exception {
        try {
            return getBenutzerDAO().findAll();
        } catch (Exception e) {
            String msg = "Benutzer konnten nicht gefunden werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

    public List<RolleTyp> alleRollen() throws Exception {

        List<RolleTyp> liste = new ArrayList<RolleTyp>();

        liste.add(RolleTyp.KASSE_MITARBEITER);
        liste.add(RolleTyp.FILIALE_SACHBEARBEITER);
        liste.add(RolleTyp.FILIALE_LEITER);
        liste.add(RolleTyp.ADMINISTRATOR);

        return liste;
    }

}
