package ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Regal;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.RegalDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.JPAUtil;

public class RegalDAOImpl extends GenericPersisterDAOImpl<Regal> implements RegalDAO {

    private static final Logger logger = LogManager.getLogger(RegalDAOImpl.class);

    public RegalDAOImpl() {
        super(Regal.class);
    }

    public Regal findByBezeichnung(String bezeichnung) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<Regal> query = em.createNamedQuery("Regal.findByBezeichnung", Regal.class);

        query.setParameter("bezeichnung", bezeichnung);

        List<Regal> liste = query.getResultList();

        em.close();

        if (liste.isEmpty()) {
            return null;
        } else if (liste.size() == 1) {
            return liste.get(0);
        } else {
            String message = "Mehr als eine Regal-Entity mit Bezeichnung \'" + bezeichnung + "\' gefunden";
            logger.error(message);
            throw new IllegalStateException(message);
        }
    }

    public Regal findByNummer(int nummer) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<Regal> query = em.createNamedQuery("Regal.findByNummer", Regal.class);

        query.setParameter("nummer", nummer);

        List<Regal> liste = query.getResultList();

        em.close();

        if (liste.isEmpty()) {
            return null;
        } else if (liste.size() == 1) {
            return liste.get(0);
        } else {
            String message = "Mehr als eine Regal-Entity mit Nummer \'" + nummer + "\' gefunden";
            logger.error(message);
            throw new IllegalStateException(message);
        }
    }
}
