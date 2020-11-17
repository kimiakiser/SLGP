package ch.hslu.informatik.moebelhandel.moebelhaus.business.lager;

import java.util.GregorianCalendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.api.MoebelhausLagerService;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Bestellung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lieferant;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Moebelhaus;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Produkt;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Regal;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Ruecksendung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Tablar;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.BestellungDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.LieferantDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.MoebelhausDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.ProduktDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.ProduktTypDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.RegalDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.RuecksendungDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.TablarDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.BestellungDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.LieferantDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.MoebelhausDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.ProduktDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.ProduktTypDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.RegalDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.RuecksendungDAOImpl;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.TablarDAOImpl;

public class MoebelhausLagerManager implements MoebelhausLagerService {

    private static Logger logger = LogManager.getLogger(MoebelhausLagerManager.class);

    private BestellungDAO bestellungDAO;
    private ProduktDAO produktDAO;
    private RegalDAO regalDAO;
    private ProduktTypDAO produktTypDAO;
    private LieferantDAO lieferantDAO;
    private MoebelhausDAO moebelhausDAO;
    private RuecksendungDAO ruecksendungDAO;

    public BestellungDAO getBestellungDAO() {

        if (bestellungDAO == null) {
            bestellungDAO = new BestellungDAOImpl();
        }

        return bestellungDAO;
    }

    public ProduktDAO getProduktDAO() {

        if (produktDAO == null) {
            produktDAO = new ProduktDAOImpl();
        }

        return produktDAO;
    }

    public RegalDAO getRegalDAO() {

        if (regalDAO == null) {
            regalDAO = new RegalDAOImpl();
        }

        return regalDAO;
    }

    public ProduktTypDAO getProduktTypDAO() {

        if (produktTypDAO == null) {
            produktTypDAO = new ProduktTypDAOImpl();
        }

        return produktTypDAO;
    }

    public LieferantDAO getLieferantDAO() {

        if (lieferantDAO == null) {
            lieferantDAO = new LieferantDAOImpl();
        }

        return lieferantDAO;
    }

    public MoebelhausDAO getMoebelhausDAO() {

        if (moebelhausDAO == null) {
            moebelhausDAO = new MoebelhausDAOImpl();
        }

        return moebelhausDAO;
    }

    public RuecksendungDAO getRuecksendungDAO() {

        if (ruecksendungDAO == null) {
            ruecksendungDAO = new RuecksendungDAOImpl();
        }

        return ruecksendungDAO;
    }

    public Bestellung bestellen(Bestellung bestellung) throws Exception {
        try {
            return getBestellungDAO().save(bestellung);
        } catch (Exception e) {
            String msg = "Bestellung konnte nicht durchgeführt werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

    public List<Produkt> produktBestand(ProduktTyp produktTyp) throws Exception {
        try {
            return getProduktDAO().findByProduktTyp(produktTyp);
        } catch (Exception e) {
            String msg = "Produktbestand für den ProduktTyp \'" + produktTyp.getName() + "\' konnte nicht abgefragt werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

    public List<Produkt> produktBestand(String produktTypCode) throws Exception {
        try {
            return getProduktDAO().findByProduktTypCode(produktTypCode);
        } catch (Exception e) {
            String msg = "Produktbestand für den ProduktTyp-Code \'" + produktTypCode + "\' konnte nicht abgefragt werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

    public void produktEinlagern(ProduktTyp produktTyp, int anzahl) throws Exception {

        ProduktDAO dao = getProduktDAO();

        for (int i = 0; i < anzahl; i++) {

            try {
                Produkt p = new Produkt(produktTyp, getProduktCode());
                dao.save(p);
                logger.info("Produkt eingelagert: [ProduktTyp-Code: " + produktTyp.getTypCode() + ", ProduktCode: " + p.getCode());
            } catch (Exception e) {
                String msg = "Die Einlagerung ist ganz oder teilweise misslungen";
                logger.error(msg, e);
                throw new Exception(msg);
            }
        }

    }

    public void produktAuslagern(List<Produkt> produktListe) throws Exception {

        ProduktDAO dao = getProduktDAO();

        for (Produkt p : produktListe) {
            p.setVerkauft(true);
            try {
                dao.update(p);
            } catch (Exception e) {
                String msg = "Produkt konnte nicht ausgelagert und als 'verkauft' markiert werden";
                logger.error(msg, e);
                throw new Exception(msg);
            }
        }
    }

    public Regal regalHinzufuegen(int nummer, int anzahlTablare) throws Exception {

        Regal regal = new Regal(nummer);
        regal.setBezeichnung("R" + nummer);

        String tablarBezeichnung = "";
        int i = 0;

        for (; i < anzahlTablare / 4; i++) {

            for (int j = 0; j < 4; j++) {

                tablarBezeichnung = regal.getBezeichnung() + "-T" + (char) (65 + j) + i;

                Tablar tablar = new Tablar(tablarBezeichnung);

                regal.addTablar(tablar);

                tablarBezeichnung = "";
            }

        }

        tablarBezeichnung = "T" + (char) (65 + i);

        for (int j = 0; j < anzahlTablare % 4; j++) {

            tablarBezeichnung = regal.getBezeichnung() + "-T" + (char) (65 + j) + i;

            Tablar tablar = new Tablar(tablarBezeichnung);

            regal.addTablar(tablar);

            tablarBezeichnung = "";
        }

        try {

            /* Alle Tablare speichern */
            TablarDAO tablarDAO = new TablarDAOImpl();

            for (Tablar t : regal.getTablarListe()) {
                tablarDAO.save(t);
            }

            /* Regal speichern */
            return getRegalDAO().save(regal);
        } catch (Exception e) {
            String msg = "Regal konnte nicht hinzugefügt werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

    public Regal regalAktualisieren(Regal regal) throws Exception {
        try {
            return getRegalDAO().update(regal);
        } catch (Exception e) {
            String msg = "Regal konnte nicht aktualisiert werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

    public void regalLoeschen(Regal regal) throws Exception {
        try {
            getRegalDAO().delete(regal);
        } catch (Exception e) {
            String msg = "Regal konnte nicht gelöscht werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

    public Regal regalByBezeichnung(String bezeichnung) throws Exception {
        try {
            return getRegalDAO().findByBezeichnung(bezeichnung);
        } catch (Exception e) {
            String msg = "Regal mit Bezeichnung \'" + bezeichnung + "\' konnte nicht gefunden werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

    public Regal regalByNummer(int nummer) throws Exception {
        try {
            return getRegalDAO().findByNummer(nummer);
        } catch (Exception e) {
            String msg = "Regal mit Nummer \'" + nummer + "\' konnte nicht gefunden werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

    public List<Regal> alleRegale() throws Exception {
        try {
            return getRegalDAO().findAll();
        } catch (Exception e) {
            String msg = "Regal konnten nicht gefunden werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

    public Regal regalByTablar(Tablar tablar) throws Exception {
        List<Regal> regalListe = alleRegale();

        for (Regal r : regalListe) {
            if (r.getTablarListe().contains(tablar)) {
                return r;
            }
        }

        return null;
    }

    public List<ProduktTyp> produktTypAlle() throws Exception {
        try {
            return getProduktTypDAO().findAll();
        } catch (Exception e) {
            String msg = "Produkt-Typen konnten nicht gefunden werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

    public void produktTypLoeschen(ProduktTyp produktTyp) throws Exception {
        try {
            getProduktTypDAO().delete(produktTyp);
        } catch (Exception e) {
            String msg = "Produkt-Typ konnten nicht gelöscht werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }

    }

    public ProduktTyp produktTypHinzufuegen(ProduktTyp produktTyp) throws Exception {
        try {
            return getProduktTypDAO().save(produktTyp);
        } catch (Exception e) {
            String msg = "Produkt-Typ konnten nicht hinzugefügt werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }

    }

    public ProduktTyp produktTypUpdaten(ProduktTyp produktTyp) throws Exception {
        try {
            return getProduktTypDAO().update(produktTyp);
        } catch (Exception e) {
            String msg = "Produkt-Typ konnten nicht aktualisiert werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

    public Lieferant lieferantenHinzufuegen(Lieferant lieferant) throws Exception {
        try {
            return getLieferantDAO().save(lieferant);
        } catch (Exception e) {
            String msg = "Lieferant konnten nicht hinzugefügt werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }

    }

    public Lieferant lieferantenAktualisieren(Lieferant lieferant) throws Exception {
        try {
            return getLieferantDAO().update(lieferant);
        } catch (Exception e) {
            String msg = "Lieferant konnten nicht aktualisiert werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }

    }

    public void lieferantenLoeschen(Lieferant lieferant) throws Exception {
        try {
            getLieferantDAO().delete(lieferant);
        } catch (Exception e) {
            String msg = "Lieferant konnten nicht gelöscht werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }

    }

    public List<Lieferant> alleLieferanten() throws Exception {
        try {
            return getLieferantDAO().findAll();
        } catch (Exception e) {
            String msg = "Lieferanten konnten nicht gefunden werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

    public List<ProduktTyp> findeByLieferant(Lieferant lieferant) throws Exception {
        try {
            return getProduktTypDAO().findByLieferant(lieferant);
        } catch (Exception e) {
            String msg = "Produkt-Typen des Lieferanten \'" + lieferant.getName() + "\' konnten nicht gefunden werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

    public List<ProduktTyp> findeByProduktname(String name) throws Exception {
        try {
            return getProduktTypDAO().findByName(name);
        } catch (Exception e) {
            String msg = "Produkt-Type für den namen \'" + name + "\' konnten nicht gefunden werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

    public ProduktTyp findeByProduktTypCode(String produktTypCode) throws Exception {
        try {
            return getProduktTypDAO().findByTypCode(produktTypCode);
        } catch (Exception e) {
            String msg = "Produkt-Type für den Produkt-Code \'" + produktTypCode + "\' konnten nicht gefunden werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

    public long getProduktCode() {
        return new GregorianCalendar().getTimeInMillis();
    }

    public Moebelhaus getMoebelhaus() throws Exception {
        try {
            return getMoebelhausDAO().findAll().get(0);
        } catch (Exception e) {
            String msg = "Möbelhaus konnten nicht gefunden werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

    public List<Bestellung> alleBestellungen() throws Exception {
        try {
            return getBestellungDAO().findAll();
        } catch (Exception e) {
            String msg = "Bestellungen konnten nicht gefunden werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

    public Bestellung findeBestellungById(long id) throws Exception {
        try {
            return getBestellungDAO().findById(id);
        } catch (Exception e) {
            String msg = "Bestellung mit Id \'" + id + "\' konnten nicht gefunden werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

    public void produktRetournieren(Produkt produkt) {
        // TODO Auto-generated method stub

    }

    public void ruecksendungHinzufuegen(Ruecksendung ruecksendung) throws Exception {
        try {
            getRuecksendungDAO().save(ruecksendung);
        } catch (Exception e) {
            String msg = "Rücksendung konnten nicht hizugefügt werden";
            logger.error(msg, e);
            throw new Exception(msg);
        }
    }

}
