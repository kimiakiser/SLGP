package ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lieferant;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.ProduktTypDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.JPAUtil;

public class ProduktTypDAOImpl extends GenericPersisterDAOImpl<ProduktTyp> implements ProduktTypDAO {

    private static final Logger logger = LogManager.getLogger(ProduktTypDAOImpl.class);

    public ProduktTypDAOImpl() {
        super(ProduktTyp.class);
    }

    public List<ProduktTyp> findByName(String name) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<ProduktTyp> query = em.createNamedQuery("ProduktTyp.findByName", ProduktTyp.class);

        query.setParameter("name", name);

        List<ProduktTyp> liste = query.getResultList();

        em.close();

        return liste != null ? liste : new ArrayList<ProduktTyp>();
    }

    public ProduktTyp findByTypCode(String typCode) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<ProduktTyp> query = em.createNamedQuery("ProduktTyp.findByTypCode", ProduktTyp.class);

        query.setParameter("typCode", typCode);

        List<ProduktTyp> liste = query.getResultList();

        em.close();

        if (liste.isEmpty()) {
            return null;
        } else if (liste.size() == 1) {
            return liste.get(0);
        } else {
            String message = "Mehr als eine ProduktTyp-Entity mit dem Code \'" + typCode + "\' gefunden";
            logger.error(message);
            throw new IllegalStateException(message);
        }
    }

    public List<ProduktTyp> findByLieferant(Lieferant lieferant) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<ProduktTyp> query = em.createNamedQuery("ProduktTyp.findByLieferant", ProduktTyp.class);

        query.setParameter("lieferant", lieferant);

        List<ProduktTyp> liste = query.getResultList();

        em.close();

        return liste != null ? liste : new ArrayList<ProduktTyp>();
    }

}
