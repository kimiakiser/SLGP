package ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.RolleTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.BenutzerDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.JPAUtil;

public class BenutzerDAOImpl extends GenericPersisterDAOImpl<Benutzer> implements BenutzerDAO {

    private static final Logger logger = LogManager.getLogger(BenutzerDAOImpl.class);

    public BenutzerDAOImpl() {
        super(Benutzer.class);
    }

    public Benutzer findByBenutzername(String benutzername) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<Benutzer> query = em.createNamedQuery("Benutzer.findByBenutzername", Benutzer.class);

        query.setParameter("benutzername", benutzername);

        List<Benutzer> liste = query.getResultList();

        em.close();

        if (liste.isEmpty()) {
            return null;
        } else if (liste.size() == 1) {
            return liste.get(0);
        } else {
            String message = "Mehr als eine Benutzer-Entity mit dem Benutzernamen \'" + benutzername + "\' gefunden";
            logger.error(message);
            throw new IllegalStateException(message);
        }
    }

    public List<Benutzer> findByRolleTyp(RolleTyp rolleTyp) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<Benutzer> query = em.createNamedQuery("Benutzer.findByRolleTyp", Benutzer.class);

        query.setParameter("rolleTyp", rolleTyp);

        List<Benutzer> liste = query.getResultList();

        em.close();

        return liste != null ? liste : new ArrayList<Benutzer>();
    }

    public List<Benutzer> findByNachnameUndVorname(String nachname, String vorname) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<Benutzer> query = em.createNamedQuery("Benutzer.findByBenutzername", Benutzer.class);

        query.setParameter("nachname", nachname);
        query.setParameter("vorname", vorname);

        List<Benutzer> liste = query.getResultList();

        em.close();

        return liste != null ? liste : new ArrayList<Benutzer>();
    }
}
